package com.hr.api.module.employeemanagment.department.request;

import com.hr.api.common.annotation.Exists;
import com.hr.api.common.annotation.Unique;
import com.hr.api.module.employeemanagment.department.Department;

import lombok.Data;

@Data
public class UpdateDepartmentRequest {
  	
	@Unique(entity = Department.class, fieldName = "name", updating = true)
	private String name;
    
	private String description;
    
	@Exists(entity = Department.class)
	private Long parentId;
    
	private Boolean active;
}
