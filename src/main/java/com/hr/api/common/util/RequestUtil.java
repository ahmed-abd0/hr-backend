package com.hr.api.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hr.api.module.usermanagement.user.User;
import com.hr.api.module.usermanagement.user.UserRepository;
import com.hr.api.module.usermanagement.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RequestUtil {

		private final UserService userService;

		public static HttpServletRequest getCurrentRequest() {
		    ServletRequestAttributes attributes =
		        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		    return attributes != null ? attributes.getRequest() : null;
		}
		
		
		public static long getIdFromPath() {
			
			String pathId = getCurrentRequest().getRequestURI().replaceAll("[^0-9]", ""); 
		    Long idFromPath = Long.parseLong(pathId);
		    
		    return idFromPath;
		}
		
		public User getCurrentUser() {
	        
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        UserDetails principal = (UserDetails) auth.getPrincipal();
	        
	        return  userService.getByEmail(principal.getUsername()).get();
	    }
	
}
