package com.indian.indian.config;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// @Component
public class AdminFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("in Filter");
        // Check for the user session
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("isAdmin") != null) {
            // User is authenticated; proceed with the request
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect("/signin");
        }
    }
}
