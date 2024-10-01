package com.indian.indian.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class MasterAdminFilter implements Filter {
    @Autowired
    private HttpSession session;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) res;
        String requestedUri = httpRequest.getRequestURI();
        if (requestedUri.startsWith("hgg/admin/master-admin")) {
            // Check if the user is logged in and authorize
            if (session == null && session.getAttribute("isMasterAdmin") == null) {
                // set user is not authorize for
                httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
        }
        chain.doFilter(httpRequest, httpResponse);
    }

}
