package com.hagt.core;

import com.hagt.core.annotation.Controller;
import com.hagt.core.annotation.Scope;
import com.hagt.core.iface.Mapping;
import com.hagt.core.iface.MappingFunction;
import com.hagt.core.impl.DefaultHanding;
import com.hagt.core.impl.DefaultMapping;
import com.hagt.core.impl.DefaultMappingFunction;
import com.hagt.enums.ControllerScope;
import com.hagt.uitl.JudgeUtil;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class MvcConfigLoad {

    private Set<String> classNames = new HashSet<>(999);
    private Set<Class> classes = new HashSet<>(999);
    private Map<Class,Set<Class>> classMap = new HashMap<>();
    private Mapping mapping;
    private Handing handing;

    public Set<String> getClassNames() {
        return classNames;
    }

    private void setClassNames(Set<String> classNames) {
        this.classNames = classNames;
    }

    public Set<Class> getClasses() {
        return classes;
    }

    private void setClasses(Set<Class> classes) {
        this.classes = classes;
    }

    private void setMapping(Mapping mapping) {
        this.mapping = mapping;
    }

    private void setHanding(Handing handing) {
        this.handing = handing;
    }

    public Mapping getMapping()
    {
        return this.mapping;
    }

    public Handing getHanding()
    {
        return this.handing;
    }

    public Map<Class, Set<Class>> getClassMap() {
        return classMap;
    }

    private void setClassMap(Map<Class, Set<Class>> classMap) {
        this.classMap = classMap;
    }

    public static MvcConfigLoad load
    (
        String scanPackage
    )
    {
        MvcConfigLoad mvcLoadAndConfig = new MvcConfigLoad();
        boolean isRecursion = true;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = scanPackage.replace(".", "/");
        URL url = loader.getResource(packagePath);
        String protocol = url.getProtocol();
        if (JudgeUtil.isFileString(protocol))
        {
            mvcLoadAndConfig.setClassNames(getClassNameFromDir(url.getPath(), scanPackage, isRecursion));
            mvcLoadAndConfig.setClasses(loadClassAll(mvcLoadAndConfig.getClassNames()));
            mvcLoadAndConfig.setClassMap(loadClassMap(mvcLoadAndConfig.getClasses()));
            mvcLoadAndConfig.setMapping(loadMapping(mvcLoadAndConfig.getClassMap().get(Controller.class)));
            mvcLoadAndConfig.setHanding(loadHanding(mvcLoadAndConfig.getClassMap().get(Handing.class),mvcLoadAndConfig.getMapping()));
        }
        return mvcLoadAndConfig;
    }

    private static Map<Class, Set<Class>> loadClassMap(Set<Class> classes) {
        Map<Class, Set<Class>> classSetMap = new HashMap<>();
        Set<Class> controllerClass = new HashSet<>();
        Set<Class> handingClass = new HashSet<>();
        for (Class c : classes)
        {
            Controller controller = (Controller) c.getDeclaredAnnotation(Controller.class);
            if (!JudgeUtil.isNull(controller))
            {
                controllerClass.add(c);
            }

            if (c.getSuperclass() == Handing.class)
            {
                handingClass.add(c);
            }
        }
        classSetMap.put(Controller.class,controllerClass);
        classSetMap.put(Handing.class,handingClass);
        return classSetMap;
    }

    private static Handing loadHanding(Set<Class> classes, Mapping mapping)
    {
        DefaultHanding lastDefaultHanding = new DefaultHanding((DefaultMapping) mapping);
        Handing handing = null;
        List<DefaultHanding> defaultHandingList = new ArrayList<>();
        try{
            for (Class  c : classes)
            {
                if (JudgeUtil.isNull(handing))
                {
                    handing = (Handing) c.newInstance();
                }
                else
                {
                    Handing tempHanding = (Handing) c.newInstance();
                    handing.nextHanding = tempHanding;
                    handing = tempHanding;
                }
            }
            if (JudgeUtil.isNull(handing))
            {
                handing = lastDefaultHanding;
            }
            else
            {
                handing.nextHanding = lastDefaultHanding;
            }
        }
        catch
        (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch
        (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return handing;
    }

    private static Mapping loadMapping(Set<Class> classes)
    {
        Map<String, MappingFunction> mappingFunctions = new HashMap<>();
        for (Class  c : classes)
        {
            Controller controller = (Controller) c.getDeclaredAnnotation(Controller.class);
            String baseMappingUrl = controller.baseUrl();
            Method[] methods = c.getMethods();
            Scope scope = (Scope) c.getDeclaredAnnotation(Scope.class);
            ControllerScope controllerScope = ControllerScope.SINGLETON;
            if (JudgeUtil.isNotNull(scope)){
                controllerScope = scope.getScope();
            }
            for (Method method : methods)
            {
                com.hagt.core.annotation.MappingFunction mappingFunction = method.getDeclaredAnnotation(com.hagt.core.annotation.MappingFunction.class);
                if (JudgeUtil.isNull(mappingFunction))
                {
                    continue;
                }
                String methodName = method.getName();
                String mapingUrl = mappingFunction.url();
                if (JudgeUtil.isNull(mapingUrl))
                {
                    mapingUrl = formatMappingUrl(methodName);
                }
                if (JudgeUtil.isNotNull(baseMappingUrl))
                {
                    mapingUrl = formatMappingUrl(baseMappingUrl) + formatMappingUrl(mapingUrl);
                }
                DefaultMappingFunction defaultMappingFunction = new DefaultMappingFunction(mapingUrl,method,c,controllerScope);
                mappingFunctions.put(mapingUrl,defaultMappingFunction);
            }
        }
        DefaultMapping defaultMapping = new DefaultMapping(mappingFunctions);
        return defaultMapping;
    }

    private static String formatMappingUrl(String mapingUrl){
        if (!mapingUrl.startsWith("/"))
        {
            mapingUrl = "/" + mapingUrl;
        }
        return mapingUrl.replaceAll("/+","/");
    }

    private static Set<Class> loadClassAll(Set<String> classNames)
    {
        Set<Class> classes = new HashSet<>();
        ClassLoader classLoader = MvcConfigLoad.class.getClassLoader();
        try
        {
            for (String className : classNames)
            {
                Class<?> aClass = classLoader.loadClass(className);
                classes.add(aClass);
            }
        }
        catch
        (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return classes;
    }

    /**
     * 从项目文件获取某包下有类
     * @param filePath    文件路径
     * @param isRecursion 是否遍历子包
     * @return 类的完整名称
     */
    private static Set<String> getClassNameFromDir(String filePath, String packageName, boolean isRecursion)
    {
        Set<String> className = new HashSet<>();
        File file = new File(filePath);
        File[] files = file.listFiles();
        for (File childFile : files)
        {
            if (childFile.isDirectory())
            {
                if (isRecursion)
                {
                    className.addAll(getClassNameFromDir(childFile.getPath(), packageName + "." + childFile.getName(), isRecursion));
                }
            }
            else
            {
                String fileName = childFile.getName();
                if (fileName.endsWith(".class") && !fileName.contains("$"))
                {
                    className.add(packageName + "." + fileName.replace(".class", ""));
                }
            }
        }
        return className;
    }

}
