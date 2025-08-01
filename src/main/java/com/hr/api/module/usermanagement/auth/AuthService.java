package com.hr.api.module.usermanagement.auth;


import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.hr.api.module.usermanagement.auth.contract.UserAuthService;
import com.hr.api.module.usermanagement.auth.request.LoginRequest;
import com.hr.api.module.usermanagement.auth.request.RegisterRequest;
import com.hr.api.module.usermanagement.user.UserMapper;
import com.hr.api.module.usermanagement.user.dto.UserDto;
import com.hr.api.security.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final UserAuthService userService;
	
	private final UserMapper userMapper;
	
    private final AuthenticationManager authenticationManager;
	
    private final JwtUtil jwtUtil;
	
	public UserDto register(RegisterRequest registerRequest) {
		
		return this.userMapper.toDto(this.userService.register(registerRequest));
		
	}

	
	public Authentication authenticate(LoginRequest loginRequest) {
		
		return this.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
	}
	
	public Authentication authenticate(String email, String password) {
		
		return authenticationManager.authenticate(
    	        new UsernamePasswordAuthenticationToken(
        	            email,
        	            password
        	        )
        	    );
	}
	
	public String generateJwtToken(String email) {
		
		return jwtUtil.generateToken(email);
	}
	
	public String generateJwtToken(String email, Map<String, Object> claims) {
		
		return jwtUtil.generateToken(email, claims);
	}

	
	public UserDto getUserByEmail(String email) {
		
		return this.userService.getDtoByEmail(email);
	}


	
}





