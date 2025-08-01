package com.hr.api.module.employeemanagment.department.dto;

import lombok.Data;

@Data
public class DepartmentWithoutEmployeesDto {
	private Long id;

    private String name;

    private String description;

    private DepartmentDto parent;

    private Boolean active = true;
}
