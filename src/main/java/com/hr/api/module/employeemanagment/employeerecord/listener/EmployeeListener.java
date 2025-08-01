package com.hr.api.module.employeemanagment.employeerecord.listener;

import java.lang.System.Logger;
import java.util.Objects;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.hr.api.module.employeemanagment.employee.Employee;
import com.hr.api.module.employeemanagment.employee.event.EmployeeUpdatedEvent;
import com.hr.api.module.employeemanagment.employeerecord.EmployeeRecord;
import com.hr.api.module.employeemanagment.employeerecord.EmployeeRecordRepository;
import com.hr.api.module.employeemanagment.employeerecord.enums.EmployeeActionType;
import com.hr.api.module.usermanagement.user.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmployeeListener {
	
	private final EmployeeRecordRepository employeeRecordRepository;
	
	private User user;
	
	@Async
	@EventListener
 	public	void onEmployeeUpdated(EmployeeUpdatedEvent employeeUpdatedEvent) {

		this.user = employeeUpdatedEvent.getUser();
		
				
		Employee before = employeeUpdatedEvent.getEmployeeBeforeUpdate();
		Employee after = employeeUpdatedEvent.getEmployeeAfterUpdate();
		
		recordDepartmentUpdate(before, after);
		recordJobTitleUpdate(before, after);
		recordPositionUpdate(before, after);
		recordSalaryUpdate(before, after);
		recordWorkLocationUpdate(before, after);
		recordDateOfBirthUpdate(before, after);
		recordEducationalQualificationUpdate(before, after);
		recordNameUpdate(before, after);
		
	}
	
	
	private void recordDepartmentUpdate(Employee before, Employee after) {
		
		if(before.getDepartment() != null && after.getDepartment() != null && !before.getDepartment().getId().equals(after.getDepartment().getId())) {
	
			saveRecord(after, EmployeeActionType.DEPARTMENT_CHANGE, 
					String.format("Employee moved from department '%s' to department '%s'.", before.getDepartment().getName(), after.getDepartment().getName()));
		}
	}
	
	
	private void recordPositionUpdate(Employee before, Employee after) {
	    
		if (!Objects.equals(before.getPosition(), after.getPosition())) {
	        saveRecord(after, EmployeeActionType.POSITION_CHANGE,
	                String.format("Position changed from '%s' to '%s'.", before.getPosition(), after.getPosition()));
	    }
	}
	
	private void recordSalaryUpdate(Employee before, Employee after) {
	 
		if (Double.compare(before.getSalary(), after.getSalary()) != 0) {
	        saveRecord(after, EmployeeActionType.SALARY_CHANGE,
	                String.format("Salary changed from %.2f to %.2f.", before.getSalary(), after.getSalary()));
	    }
	}
	
	private void recordJobTitleUpdate(Employee before, Employee after) {
	    
		if (!Objects.equals(before.getJobTitle(), after.getJobTitle())) {
	        saveRecord(after, EmployeeActionType.JOB_TITLE_CHANGE,
	                String.format("Job title changed from '%s' to '%s'.", before.getJobTitle(), after.getJobTitle()));
	    }
	}
	
	private void recordWorkLocationUpdate(Employee before, Employee after) {
	  
		if (!Objects.equals(before.getWorkLocation(), after.getWorkLocation())) {
	        saveRecord(after, EmployeeActionType.WORK_LOCATION_CHANGE,
	                String.format("Work location changed from '%s' to '%s'.", before.getWorkLocation(), after.getWorkLocation()));
	    }
	}
	
	private void recordEducationalQualificationUpdate(Employee before, Employee after) {
	   
		if (!Objects.equals(before.getEducationalQualification(), after.getEducationalQualification())) {
	        saveRecord(after, EmployeeActionType.EDUCATIONAL_QUALIFICATION_CHANGE,
	                String.format("Educational qualification changed from '%s' to '%s'.",
	                        before.getEducationalQualification(), after.getEducationalQualification()));
	    }
	}
	
	private void recordDateOfBirthUpdate(Employee before, Employee after) {
	    if (!Objects.equals(before.getDateOfBirth(), after.getDateOfBirth())) {
	        saveRecord(after, EmployeeActionType.DATE_OF_BIRTH_CHANGE,
	                String.format("Date of birth changed from '%s' to '%s'.", before.getDateOfBirth(), after.getDateOfBirth()));
	    }
	}
	
	private void recordNameUpdate(Employee before, Employee after) {
	    if (!Objects.equals(before.getName(), after.getName())) {
	        saveRecord(after, EmployeeActionType.NAME_CHANGE,
	                String.format("Name changed from '%s' to '%s'.", before.getName(), after.getName()));
	    }
	}
	
	private void saveRecord(Employee employee, EmployeeActionType type, String description) {
	   
		EmployeeRecord record = EmployeeRecord.builder()
	            .employee(employee)
	            .actionType(type)
	            .description(description)
	            .user(user)
	            .build();
		 
	    employeeRecordRepository.save(record);
	}
}







