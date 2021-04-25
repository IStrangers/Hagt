package com.hagt.core.impl;

import com.hagt.core.Handing;
import com.hagt.core.iface.MappingFunction;
import com.hagt.core.enums.RequestType;
import com.hagt.uitl.JudgeUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;

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
            Object[] invokeMethodParams = getInvokeMethodParams(req,method);
            Object ownerObject = mappingFunction.getOwnerObject();
            Object result = method.invoke(ownerObject,invokeMethodParams);
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

    private Object [] getInvokeMethodParams
    (
        HttpServletRequest req, Method method
    )
    {
        int parameterCount = method.getParameterCount();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object [] params = new Object[parameterTypes.length];
        try
        {
            Map<String, String[]> parameterMap = req.getParameterMap();
            Enumeration<String> headerNames = req.getHeaderNames();
            String methodType = req.getMethod();
            if (RequestType.GET != RequestType.valueOf(methodType))
            {

            }
            ServletInputStream inputStream = req.getInputStream();
            if (true){
                params[parameterCount - 1] = parameterMap;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return params;
    }

    private void render(Object result,HttpServletResponse res)
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
