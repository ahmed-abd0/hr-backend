package com.hr.api.security.oauth;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hr.api.module.usermanagement.user.User;
import com.hr.api.module.usermanagement.user.UserMapper;
import com.hr.api.module.usermanagement.user.UserService;
import com.hr.api.security.oauth.providers.OAuthProvider;
import com.hr.api.security.oauth.providers.OAuthProviderFactory;
import com.hr.api.security.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtService;
    
    @Lazy
    @Autowired
    private  UserService userService;
    
    private final UserMapper userMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        

    	OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
    	   
        OAuthProvider oAuthProvider = OAuthProviderFactory.getProvider(token.getAuthorizedClientRegistrationId());
        
        Map<String, String> userData= oAuthProvider.getAuthInfo(token);
       
        User user = userService.findOrCreateOAuthUser(userData);
        
        String jwt = jwtService.generateToken(user.getEmail(), Map.of("id", user.getId()));

        response.setContentType("application/json");
        
        String jsonResponse = new ObjectMapper().writeValueAsString(Map.of(
                "token", jwt,
                "user", this.userMapper.toDto(user),
                "message", "OAuth2 login successful"
            ));
        
        
        response.getWriter().write(jsonResponse);
    }
}


