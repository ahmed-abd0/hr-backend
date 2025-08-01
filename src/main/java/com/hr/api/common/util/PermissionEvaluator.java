package com.hr.api.common.util;

import org.springframework.stereotype.Component;

import com.hr.api.module.usermanagement.user.User;
import com.hr.api.module.usermanagement.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component("permissionEvaluator")
@Slf4j
@RequiredArgsConstructor
public class PermissionEvaluator {
	
	private final RequestUtil requestUtil;
	
	private final UserService userService;
	
	public boolean hasPermission(String permission) {
		
		User user = requestUtil.getCurrentUser();
		
		//give admin all permissions
		if(user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"))) {
			return true;
		}
		
	    	 
		return  userService.hasPermission(user ,permission);
	}
}
