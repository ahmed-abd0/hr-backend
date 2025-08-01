package com.hr.api.module.usermanagement.auth.request;

import java.util.List;

import com.hr.api.common.annotation.StrongPassword;
import com.hr.api.common.annotation.Unique;
import com.hr.api.module.usermanagement.user.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record RegisterRequest(

		@NotBlank(message = "{user.name.required}")
		String name,

		@NotBlank(message = "{user.password.required}")
		@StrongPassword()
		String password,

		Long employeeId,

		boolean enabled,

		@NotBlank(message = "{user.email.required}")
		@Email(message = "{user.email.invalid}")
		@Unique(fieldName = "email", entity = User.class, message = "{user.email.exists}")	
		String email,

		@NotEmpty(message = "{user.roles.required}")
		List<@NotBlank(message = "{user.roles.invalid}") Long> roles

	) {}