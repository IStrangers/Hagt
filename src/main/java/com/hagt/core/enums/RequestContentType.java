package com.hagt.core.enums;

import java.util.Arrays;
import java.util.List;

public enum RequestContentType {

    MULTIPART_FORM_DATA("multipart/form-data"),
    APPLICATION_X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded"),
    APPLICATION_JSON("application/json"),
    TEXT_XML("text/xml"),
    APPLICATION_JAVASCRIPT("application/javascript"),
    TEXT_PLAIN("text/plain"),
    TEXT_HTML("text/html");

    private String typeValue;

    RequestContentType(String typeValue)
    {
        this.typeValue = typeValue;
    }

    public String getTypeValue()
    {
        return this.typeValue;
    }
}
