package com.hagt.core.impl;

import com.hagt.core.iface.MappingFunction;
import com.hagt.core.enums.ControllerScope;
import com.hagt.core.model.MethodParam;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DefaultMappingFunction implements MappingFunction
{
    private static final Map<Class,Object> invokeObject = new HashMap<>();
    private String path;
    private Method method;
    private int methodParamCount;
    private Map<Class, MethodParam> methodParams;
    private Class ownerClass;
    private ControllerScope scope;

    public DefaultMappingFunction
    (
        String path, Method method, Class ownerClass, ControllerScope scope,int methodParamCount, Map<Class, MethodParam> methodParams
    )
    {
        this.path = path;
        this.method = method;
        this.ownerClass = ownerClass;
        if (!invokeObject.containsKey(ownerClass))
        {
            invokeObject.put(ownerClass,classToObject());
        }
        this.scope = scope;
        this.methodParamCount = methodParamCount;
        this.methodParams = methodParams;
    }

    @Override
    public String getPath()
    {
        return this.path;
    }

    @Override
    public Method getMethod()
    {
        return this.method;
    }

    @Override
    public Object getOwnerObject()
    {
        if (scope.SINGLETON == scope)
        {
            return invokeObject.get(ownerClass);
        }
        else if (scope.PROPERTY == scope)
        {
            return classToObject();
        }
        return classToObject();
    }

    @Override
    public Map<Class, MethodParam> getMethodParams() {
        return this.methodParams;
    }

    @Override
    public int getParameterCount() {
        return this.methodParamCount;
    }

    private Object classToObject()
    {
        try
        {
            return this.ownerClass.newInstance();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
