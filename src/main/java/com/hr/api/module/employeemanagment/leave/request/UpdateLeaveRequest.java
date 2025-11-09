package com.hr.api.module.employeemanagment.leave.request;

import java.time.LocalDate;

import com.hr.api.module.employeemanagment.leave.enums.LeaveStatus;
import com.hr.api.module.employeemanagment.leave.enums.LeaveType;

import lombok.Data;

@Data
public class UpdateLeaveRequest {

    private LocalDate startDate;
    
    private LocalDate endDate;
    
    private LeaveType type;
    
    private String reason;
    
    private LeaveStatus status;
}