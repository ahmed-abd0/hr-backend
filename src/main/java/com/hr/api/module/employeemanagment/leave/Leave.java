package com.hr.api.module.employeemanagment.leave;

import java.time.LocalDate;

import com.hr.api.module.employeemanagment.employee.Employee;
import com.hr.api.module.employeemanagment.leave.enums.LeaveStatus;
import com.hr.api.module.employeemanagment.leave.enums.LeaveType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@Table(name =  "leaves")
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    
    @Enumerated(EnumType.STRING)
    private LeaveType type; 

    private String reason;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status = LeaveStatus.PENDING; 

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
