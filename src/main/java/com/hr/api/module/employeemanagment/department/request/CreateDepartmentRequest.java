package com.hr.api.module.employeemanagment.department.request;

import com.hr.api.common.annotation.Exists;
import com.hr.api.common.annotation.Unique;
import com.hr.api.module.employeemanagment.department.Department;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateDepartmentRequest {
    
	@NotBlank
	@Unique(entity = Department.class, fieldName = "name")
    private String name;
    
    @NotBlank
    private String description;
    
    @Exists(entity = Department.class)
    private Long parentId;
}