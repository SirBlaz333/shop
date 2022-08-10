package com.my.web.filter;

import com.my.entity.response.GzipResponseWrapper;
import com.my.web.filter.parser.ParamParser;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GzipResponseCompressionFilter implements Filter {
    private ParamParser paramParser;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        paramParser = new ParamParser();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        List<String> acceptEncoding = paramParser.getParams(request.getHeader("Accept-Encoding"));
        if (acceptEncoding.contains("gzip")) {
            response.addHeader("Content-Encoding", "gzip");
            GzipResponseWrapper gzipResponseWrapper = new GzipResponseWrapper(response);
            filterChain.doFilter(request, gzipResponseWrapper);
            gzipResponseWrapper.close();
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
