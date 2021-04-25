package com.hagt.core.annotation;

import com.hagt.core.enums.ControllerScope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface Scope
{
    ControllerScope getScope() default ControllerScope.SINGLETON ;
}