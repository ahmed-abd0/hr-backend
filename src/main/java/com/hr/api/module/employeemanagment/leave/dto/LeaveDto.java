package com.hr.api.module.employeemanagment.leave.dto;

import java.time.LocalDate;

import com.hr.api.module.employeemanagment.leave.enums.LeaveStatus;
import com.hr.api.module.employeemanagment.leave.enums.LeaveType;

import lombok.Data;

@Data
public class LeaveDto {
 
	private Long id;
    
	private LocalDate startDate;
    
	private LocalDate endDate;
    
	private LeaveType type;
    
	private String reason;
    
	private LeaveStatus status;
    
	private Long employeeId;
}
