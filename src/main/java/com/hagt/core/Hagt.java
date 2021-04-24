package com.hagt.core;

import com.hagt.core.iface.Mapping;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Hagt
{
    private static Hagt hagt = new Hagt();

    private Mapping mapping;
    private Handing handing;
    private ServletContext servletContext;

    public static Hagt getHagt()
    {
        return hagt;
    }

    public void start
    (
        FilterConfig filterConfig
    )
    {
        servletContext = filterConfig.getServletContext();
        String scanPackage = filterConfig.getInitParameter("scanPackage");
        init(scanPackage);
    }

    public void stop()
    {
        this.hagt = null;
    }

    public void init(String scanPackage)
    {
        MvcLoadAndConfig config = MvcLoadAndConfig.load(scanPackage);
        mapping = config.getMapping();
        handing = config.getHanding();
    }

    public void accept
    (
        ServletRequest request, ServletResponse response, FilterChain chain
    )
        throws IOException, ServletException
    {
        HttpServletRequest rq = (HttpServletRequest) request;
        HttpServletResponse rs = (HttpServletResponse) response;
        String requestPath = rq.getRequestURI();
        handing.handing(requestPath,rq,rs,chain);
    }
}
