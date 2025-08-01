package com.hr.api.module.usermanagement.user.dto;



import java.util.List;

import com.hr.api.module.usermanagement.authority.AuthorityDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    
	private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    private boolean enabled;
    
    private Long employeeId;
    
    private String profileImage;

    @NotEmpty(message = "At least one role is required")
    private List<AuthorityDto> roles;
   
}

