package com.hr.api.module.employeemanagment.employee.event;

import com.hr.api.module.employeemanagment.employee.Employee;
import com.hr.api.module.usermanagement.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmployeeUpdatedEvent {

	private final User user;
	
	private final Employee employeeBeforeUpdate;
	
	private final Employee employeeAfterUpdate;

}
