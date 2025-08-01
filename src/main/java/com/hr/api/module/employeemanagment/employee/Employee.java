package com.hr.api.module.employeemanagment.employee;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hr.api.common.entity.BaseEntity;
import com.hr.api.module.employeemanagment.department.Department;
import com.hr.api.module.employeemanagment.employeerecord.EmployeeRecord;
import com.hr.api.module.usermanagement.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Employee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate dateOfBirth;

    private String position;

    private double salary;
    
    private LocalDate joinDate;
    
    private String jobTitle;
    
    private String workLocation;
    
    private String educationalQualification;
    
    private String cvUrl;
    
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private User user;
   
    @ManyToOne()
    @JoinColumn(name = "department_id")
    private Department department;
    
    @OneToMany(mappedBy = "employee")
    private Set<EmployeeRecord> employeeRecords = new HashSet<>();
}
