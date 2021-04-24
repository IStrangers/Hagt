package com.hagt.core.impl;

import com.hagt.core.Handing;
import com.hagt.core.iface.MappingFunction;
import com.hagt.uitl.JudgeUtil;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

public class DefaultHanding extends Handing {

    private DefaultMapping defaultMapping;

    public DefaultHanding
    (
        DefaultMapping defaultMapping
    )
    {
        this.defaultMapping = defaultMapping;
    }

    @Override
    public void handing
    (
        String requestPath, HttpServletRequest req, HttpServletResponse res, FilterChain chain
    )
    {
        defaultHanding(requestPath,req,res,chain);
    }

    private MappingFunction getMappingFunction
    (
        String target
    )
    {
        return defaultMapping.getMappingFunction(target);
    }

    private void defaultHanding
    (
        String requestPath, HttpServletRequest req, HttpServletResponse res, FilterChain chain
    )
    {
        try
        {
            MappingFunction mappingFunction = getMappingFunction(requestPath);
            if (JudgeUtil.isNull(mappingFunction))
            {
                render("No corresponding mapping was found",res);
                return;
            }
            Method method = mappingFunction.getMethod();
            Class<?>[] parameterTypes = method.getParameterTypes();
            Parameter[] parameters = method.getParameters();
            Object ownerObject = mappingFunction.getOwnerObject();
            Object result = method.invoke(ownerObject,null);
            render(result,res);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    public void render(Object result,HttpServletResponse res)
    {
        PrintWriter writer = null;
        try
        {
            writer = res.getWriter();
            writer.print(result);
        }
        catch
        (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (JudgeUtil.isNull(writer)) return;
            writer.flush();
            writer.close();
        }
    }
}
