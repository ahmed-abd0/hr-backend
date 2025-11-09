package com.hr.api.module.employeemanagment.leave.request;

import java.time.LocalDate;

import com.hr.api.common.annotation.Exists;
import com.hr.api.module.employeemanagment.employee.Employee;
import com.hr.api.module.employeemanagment.leave.enums.LeaveType;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateLeaveRequest {

    @NotNull
    @FutureOrPresent
    private LocalDate startDate;

    @NotNull
    @Future
    private LocalDate endDate;

    @NotNull
    private LeaveType type;

    private String reason;

    @NotNull
    @Exists(entity = Employee.class)
    private Long employeeId;
}