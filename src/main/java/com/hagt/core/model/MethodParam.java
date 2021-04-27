package com.hagt.core.model;

public class MethodParam {

    private int paramIndex;
    private String paramName;
    private Class<?> paramType;

    public MethodParam(){

    }

    public MethodParam(int paramIndex,String paramName,Class<?> paramType){
        this.paramIndex = paramIndex;
        this.paramName = paramName;
        this.paramType = paramType;
    }

    public int getParamIndex() {
        return paramIndex;
    }

    public void setParamIndex(int paramIndex) {
        this.paramIndex = paramIndex;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Class<?> getParamType() {
        return paramType;
    }

    public void setParamType(Class<?> paramType) {
        this.paramType = paramType;
    }
}
