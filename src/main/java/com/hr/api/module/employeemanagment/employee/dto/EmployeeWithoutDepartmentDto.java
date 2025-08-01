package com.hr.api.module.employeemanagment.employee.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EmployeeWithoutDepartmentDto {
	private Long id;
    
	private String name;
        
    private LocalDate dateOfBirth;

    private String position;

    private double salary;
    
    private String cvUrl;
    
    private String jobTitle;
    
    private String workLocation;
    
    private String educationalQualification;
}
