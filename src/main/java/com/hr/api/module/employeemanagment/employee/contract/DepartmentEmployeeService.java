package com.hr.api.module.employeemanagment.employee.contract;

import com.hr.api.module.employeemanagment.department.Department;

public interface DepartmentEmployeeService {
	
	Department findById(Long departmentId);
}
