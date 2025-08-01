package com.hr.api.module.employeemanagment.employee.request;

import java.time.LocalDate;

import com.hr.api.common.annotation.Exists;
import com.hr.api.module.employeemanagment.department.Department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateEmployeeRequest {

    @NotBlank
    private String name;

    @Past
    private LocalDate dateOfBirth;

    @NotBlank
    private String position;

    @Positive
    private double salary;
    
    @Past
    private LocalDate joinDate;
    
    @NotBlank
    private String jobTitle;
    
    @NotBlank
    private String workLocation;
    
    @NotBlank
    private String educationalQualification;
    
    @Exists(entity = Department.class)
    private Long departmentId;

}