package com.hr.api.module.usermanagement.authority.request;

import java.util.Set;

import com.hr.api.common.annotation.Exists;
import com.hr.api.common.annotation.Unique;
import com.hr.api.module.usermanagement.authority.Authority;
import com.hr.api.module.usermanagement.permission.Permission;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAuthorityRequest {

	@NotBlank
	@Unique(entity = Authority.class, fieldName = "name")
	private String name;
	
	private Set<@Exists(entity = Permission.class) Long> permissions;
	
}
