package com.hagt.core;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Handing
{

    protected Handing nextHanding;

    public abstract void handing
    (
            String requestPath, HttpServletRequest req, HttpServletResponse res, FilterChain chain
    );

}
