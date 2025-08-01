package com.hr.api.module.usermanagement.user.request;


import com.hr.api.common.annotation.Exists;
import com.hr.api.module.usermanagement.authority.Authority;
import com.hr.api.module.usermanagement.user.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssignAuthorityRequest {

	@Exists(entity = User.class)
	private Long userId;
	
	@NotBlank
	@Exists(entity = Authority.class)
	private Long authorityId;
}
