package com.hr.api.module.usermanagement.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.api.module.usermanagement.auth.request.LoginRequest;
import com.hr.api.module.usermanagement.auth.request.RegisterRequest;
import com.hr.api.module.usermanagement.user.dto.UserDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
   
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {

    	
    	Authentication authentication = this.authService.authenticate(loginRequest);
    	 
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        UserDto userDto = authService.getUserByEmail(userDetails.getUsername());
        
        String jwt = this.authService.generateJwtToken(userDetails.getUsername(), Map.of("id", userDto.getId()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        Map<String, Object> response = new HashMap<>();
        
        response.put("token", jwt);
        response.put("user", userDto);
        
        return ResponseEntity.ok(response);
            
    }
    
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest  registerRequest) {
    	    	
    	UserDto userDto = this.authService.register(registerRequest);
       	
        return ResponseEntity.ok(userDto);
    }
      
      
    
}
