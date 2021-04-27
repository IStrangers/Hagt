package com.hagt.core.iface;

import com.hagt.core.model.MethodParam;

import java.lang.reflect.Method;
import java.util.Map;

public interface MappingFunction
{
    String getPath();
    Method getMethod();
    Object getOwnerObject();
    Map<Class, MethodParam> getMethodParams();
    int getParameterCount();
}
