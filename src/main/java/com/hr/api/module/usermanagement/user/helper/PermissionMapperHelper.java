package com.hr.api.module.usermanagement.user.helper;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.hr.api.module.usermanagement.permission.Permission;
import com.hr.api.module.usermanagement.user.User;

@Component
public class PermissionMapperHelper {

	 public static List<String> extractPermissionNames(User user) {
	      
		 if (user == null || user.getRoles() == null) return List.of();

	        return user.getRoles().stream()
	            .filter(Objects::nonNull)
	            .flatMap(authority -> authority.getPermissions().stream())
	            .filter(Objects::nonNull)
	            .map(Permission::getName)
	            .distinct()
	            .toList();
	    }
}
