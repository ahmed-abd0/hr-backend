package com.hr.api.module.usermanagement.user.dto;


import java.util.List;

import com.hr.api.module.employeemanagment.employee.dto.EmployeeDto;
import com.hr.api.module.usermanagement.authority.AuthorityDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    
	private Long id;

    private String name;

    private String email;
    
    private boolean enabled;

    private List<AuthorityDto> roles;
    
    private EmployeeDto employee;
    
    private String profileImage;
    
    private List<String> permissions;
   
}