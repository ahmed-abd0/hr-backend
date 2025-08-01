package com.hr.api.module.usermanagement.user.request;

import java.util.ArrayList;
import java.util.List;

import com.hr.api.common.annotation.Exists;
import com.hr.api.module.usermanagement.authority.Authority;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserRequest {
	
	    private String name;

	    @Email()
	    private String email;
	    
	    private Long employeeId;

	    private boolean enabled;

	    private List<@Exists(entity = Authority.class) Long> roles = new ArrayList<>();
}
