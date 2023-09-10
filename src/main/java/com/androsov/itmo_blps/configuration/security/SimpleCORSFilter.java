package com.androsov.itmo_blps.configuration.security;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SimpleCORSFilter extends OncePerRequestFilter {

    protected static final String ALLOWED_ORIGINS = "*";

    protected static final String ALLOWED_METHODS = "GET,POST,PUT,DELETE,OPTIONS,UPDATE";
    protected static final String ALLOWED_HEADERS = "X-Requested-With,Content-Type,Accept,Origin,Authorization,X-CSRF-TOKEN";
    protected static final String ALLOW_CREDENTIALS = "true";
    protected static final String MAX_AGE = "604800";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, javax.servlet.FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", ALLOWED_ORIGINS);
        response.setHeader("Access-Control-Allow-Methods", ALLOWED_METHODS);
        response.setHeader("Access-Control-Max-Age", MAX_AGE);
        response.setHeader("Access-Control-Allow-Headers", ALLOWED_HEADERS);
        response.setHeader("Access-Control-Allow-Credentials", ALLOW_CREDENTIALS);

        filterChain.doFilter(request, response);
    }
}
