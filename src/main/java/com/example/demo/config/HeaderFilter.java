package com.example.demo.config;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class HeaderFilter implements Filter {

    private boolean chkBrowser(String userAgent) {
        return userAgent.contains("Trident") || userAgent.contains("Edge")
                || userAgent.contains("Whale") || userAgent.contains("Opera") || userAgent.contains("OPR")
                || userAgent.contains("Firefox")
                || (userAgent.contains("Safari") && !userAgent.contains("Chrome"))
                || userAgent.contains("Chrome");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 초기화 로직이 필요한 경우 여기에 추가
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String userAgent = httpRequest.getHeader("User-Agent");

        if (userAgent == null || !chkBrowser(userAgent)) {
            httpResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            httpResponse.getWriter().write("Bad Request");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 정리 로직이 필요한 경우 여기에 추가
    }
}