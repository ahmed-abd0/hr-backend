package com.hr.api.module.employeemanagment.employeerecord;


import org.springframework.data.jpa.repository.JpaRepository;

import com.hr.api.module.employeemanagment.employee.Employee;

import java.util.List;

public interface EmployeeRecordRepository extends JpaRepository<EmployeeRecord, Long> {
   
	List<EmployeeRecord> findByEmployee(Employee employee);
	
	List<EmployeeRecord> findByEmployeeId(Long employeeId);
}