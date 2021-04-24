package com.hagt.core.iface;

import java.lang.reflect.Method;

public interface MappingFunction
{
    String getPath();
    Method getMethod();
    Object getOwnerObject();
}
