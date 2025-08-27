//package com.example.storeproject.config;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebFilter(value = "*")
//public class FilterUTF8 implements Filter {
//
//    private String encoding;
//    private boolean forceEncoding;
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        encoding = filterConfig.getInitParameter("encoding");
//        if (encoding == null) {
//            encoding = "UTF-8";
//        }
//
//        String forceEncodingStr = filterConfig.getInitParameter("forceEncoding");
//        forceEncoding = forceEncodingStr != null && Boolean.parseBoolean(forceEncodingStr);
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        if (forceEncoding || request.getCharacterEncoding() == null) {
//            request.setCharacterEncoding(encoding);
//        }
//
//        if (forceEncoding || response.getCharacterEncoding() == null) {
//            response.setCharacterEncoding(encoding);
//        }
//
//        // Set content type for HTML responses
//        if (response.getContentType() == null || response.getContentType().contains("text/html")) {
//            response.setContentType("text/html; charset=" + encoding);
//        }
//
//        chain.doFilter(request, response);
//    }
//
//    @Override
//    public void destroy() {
//        // Cleanup code if needed
//    }
//}
