package com.romanidze.jizzyshop.filters;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;

import java.io.IOException;

public class CharsetFilter implements Filter{

    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        this.encoding = filterConfig.getInitParameter("requestEncoding");

        if(this.encoding == null){

            this.encoding = "UTF-8";

        }

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain){

        try {

            if (req.getCharacterEncoding() == null) {
                req.setCharacterEncoding(this.encoding);
            }

            resp.setContentType("charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");

            filterChain.doFilter(req, resp);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void destroy() {
    }
}
