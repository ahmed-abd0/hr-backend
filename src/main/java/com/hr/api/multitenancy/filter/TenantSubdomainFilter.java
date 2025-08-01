package com.hr.api.multitenancy.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hr.api.multitenancy.TenantContext;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TenantSubdomainFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        String serverName = request.getServerName();
        String subdomain = extractSubdomain(serverName);

           
        if (subdomain != null && !subdomain.isBlank()) {
            TenantContext.setTenantId(subdomain);
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear(); 
        }
    }

    private String extractSubdomain(String serverName) {
        String[] parts = serverName.split("\\.");
        return parts.length >= 2 ? parts[0] : null;
    }
}

