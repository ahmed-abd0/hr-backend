package com.hr.api.module.employeemanagment.department.dto;

import java.util.HashSet;
import java.util.Set;

import com.hr.api.module.employeemanagment.employee.dto.EmployeeWithoutDepartmentDto;

import lombok.Data;

@Data
public class DepartmentDto {
 
	private Long id;

    private String name;

    private String description;

    private DepartmentDto parent;

    private Boolean active = true;
    
    private Set<EmployeeWithoutDepartmentDto> employees = new HashSet<>();
}
