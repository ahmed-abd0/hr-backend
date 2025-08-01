package com.hr.api.module.usermanagement.user.request;

import java.util.List;

import com.hr.api.common.annotation.Exists;
import com.hr.api.common.annotation.StrongPassword;
import com.hr.api.common.annotation.Unique;
import com.hr.api.module.usermanagement.authority.Authority;
import com.hr.api.module.usermanagement.user.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {
	 
	    @NotBlank()
	    private String name;

	    @NotBlank()
	    @Email()
	    @Unique(entity = User.class, fieldName = "email")
	    private String email;
	    
	    @StrongPassword
	    private String password;

	    private Long employeeId;

	    private boolean enabled;
	    
	    @NotEmpty()
	    private List<@Exists(entity = Authority.class) Long> roles;
}




