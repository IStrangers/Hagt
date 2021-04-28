package com.hagt.core.iface;

public interface GetRequestParam {

    String getRawData(String name);
    String getParam(String name);
    String[] getParams(String name);
    String getRequestContentType();

}
