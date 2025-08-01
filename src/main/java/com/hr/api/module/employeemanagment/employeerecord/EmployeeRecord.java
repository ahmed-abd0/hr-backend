package com.hr.api.module.employeemanagment.employeerecord;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hr.api.common.entity.BaseEntity;
import com.hr.api.module.employeemanagment.employee.Employee;
import com.hr.api.module.employeemanagment.employeerecord.enums.EmployeeActionType;
import com.hr.api.module.usermanagement.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRecord extends BaseEntity {	
	
 	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmployeeActionType actionType;

    @Column(columnDefinition = "TEXT")
	private String description;
    
    
    @ManyToOne()
    @JoinColumn(name = "employee_id")
    private Employee employee;
    

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

}







