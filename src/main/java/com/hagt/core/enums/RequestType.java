package com.hagt.core.enums;

public enum RequestType {

    GET("GET"),
    POST("POST");

    private String tpyeValue;

    RequestType(String typeValue)
    {
        this.tpyeValue = typeValue;
    }
}
