package com.hr.api.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hr.api.common.exception.GlobalExceptionHandler;
import com.hr.api.security.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    
    @Autowired
    @Lazy
    private  UserDetailsService userDetailsService;
    

    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

    	
    	
        final String token = jwtUtil.getJwtFromHeader(request);
        
        
        if (token == null) {
        
            filterChain.doFilter(request, response);
            return;
        }

        String email = null;
        try {
            email = jwtUtil.extractEmail(token);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                      	
           	UserDetails user = userDetailsService.loadUserByUsername(email);
        	
            if (jwtUtil.isTokenValid(token, user.getUsername())) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities()
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            
        }

        filterChain.doFilter(request, response);
    }
}
