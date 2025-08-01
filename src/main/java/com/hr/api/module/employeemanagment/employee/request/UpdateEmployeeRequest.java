package com.hr.api.module.employeemanagment.employee.request;

import java.time.LocalDate;

import com.hr.api.common.annotation.Exists;
import com.hr.api.module.employeemanagment.department.Department;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateEmployeeRequest {

    private String name;

    @Past
    private LocalDate dateOfBirth;

    private String position;

    @Positive
    private double salary;
    
    @Past
    private LocalDate joinDate;
    
    private String jobTitle;
    
    private String workLocation;
    
    private String educationalQualification;
    
    @Exists(entity = Department.class)
    private Long departmentId;
    
}