package com.hagt.core.impl;

import com.hagt.core.iface.Mapping;
import com.hagt.core.iface.MappingFunction;

import java.util.HashMap;
import java.util.Map;

public class DefaultMapping implements Mapping
{

    private Map<String,MappingFunction> mappingFunctions = new HashMap<>(999);

    public DefaultMapping
    (
        Map<String,MappingFunction> mappingFunctions
    )
    {
        this.mappingFunctions = mappingFunctions;
    }

    @Override
    public MappingFunction getMappingFunction
    (
        Object target
    )
    {
        return mappingFunctions.get(target);
    }
}
