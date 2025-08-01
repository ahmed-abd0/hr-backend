package com.hr.api.module.employeemanagment.employee.dto;

import java.time.LocalDate;

import com.hr.api.module.employeemanagment.department.dto.DepartmentDto;
import com.hr.api.module.employeemanagment.department.dto.DepartmentWithoutEmployeesDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {
   
	private Long id;
    
	private String name;
        
    private LocalDate dateOfBirth;

    private String position;

    private double salary;
    
    private String cvUrl;
    
    private String jobTitle;
    
    private String workLocation;
    
    private String educationalQualification;
    
    private DepartmentWithoutEmployeesDto department;

}



