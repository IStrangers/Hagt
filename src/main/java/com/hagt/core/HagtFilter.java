package com.hagt.core;

import javax.servlet.*;
import java.io.IOException;

public class HagtFilter implements Filter
{

    private static final Hagt hagt = Hagt.getHagt();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        hagt.start(filterConfig);
    }

    @Override
    public void destroy()
    {
        hagt.stop();
    }

    @Override
    public void doFilter
    (
        ServletRequest request, ServletResponse response, FilterChain chain
    )
        throws IOException, ServletException
    {
        hagt.accept(request,response,chain);
    }
}
