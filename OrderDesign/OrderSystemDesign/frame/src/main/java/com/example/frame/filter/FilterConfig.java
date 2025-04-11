package com.example.frame.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/api/*", filterName = "myFilter")
@Order(1) // Execution Order.
public class FilterConfig implements Filter {
    @Override
    public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
        // Initialize.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("Before request");

        // pass
        chain.doFilter(request, response);
        System.out.println("After request");
    }

    @Override
    public void destroy() {
        // Destroy
    }

}