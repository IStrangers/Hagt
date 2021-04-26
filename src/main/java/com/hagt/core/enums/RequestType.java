package com.hagt.core.enums;

public enum RequestType {

    GET("GET"),
    POST("POST");

    private String typeValue;

    RequestType(String typeValue){
        this.typeValue = typeValue;
    }
}
