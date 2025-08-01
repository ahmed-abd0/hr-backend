package com.hr.api.module.employeemanagment.employeerecord.dto;

import com.hr.api.module.employeemanagment.employeerecord.enums.EmployeeActionType;
import com.hr.api.module.usermanagement.user.User;
import com.hr.api.module.usermanagement.user.dto.UserDto;

import lombok.Data;


@Data
public class EmployeeRecordDto {

    private Long id;

    private EmployeeActionType actionType;

	private String description;
    
    private UserDto user;

}
