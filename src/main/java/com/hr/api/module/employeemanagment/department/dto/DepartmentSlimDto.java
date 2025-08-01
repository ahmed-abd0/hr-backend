package com.hr.api.module.employeemanagment.department.dto;

import java.util.HashSet;
import java.util.Set;

import com.hr.api.module.employeemanagment.employee.dto.EmployeeDto;

import lombok.Data;

@Data
public class DepartmentSlimDto {
 
	private Long id;

    private String name;

    private String description;

    private Long parent;

    private Boolean active = true;
    
    private Set<Long> employees = new HashSet<>();
}
