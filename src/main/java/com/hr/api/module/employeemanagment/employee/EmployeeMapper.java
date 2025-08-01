package com.hr.api.module.employeemanagment.employee;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.hr.api.common.response.PagedResponse;
import com.hr.api.module.employeemanagment.department.Department;
import com.hr.api.module.employeemanagment.department.DepartmentService;
import com.hr.api.module.employeemanagment.employee.contract.DepartmentEmployeeService;
import com.hr.api.module.employeemanagment.employee.dto.EmployeeDto;
import com.hr.api.module.employeemanagment.employee.request.CreateEmployeeRequest;
import com.hr.api.module.employeemanagment.employee.request.UpdateEmployeeRequest;

@Mapper(componentModel = "spring")
public abstract class EmployeeMapper {

	@Autowired
	private  DepartmentEmployeeService departmentService;
	
    public abstract EmployeeDto toDto(Employee employee);

    public abstract List<EmployeeDto> toDtos(List<Employee> employees);

    @Mapping(source = "departmentId", target = "department")
    public abstract Employee toEntity(CreateEmployeeRequest request);
    
    @Mapping(source = "departmentId", target = "department")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateEmployeeFromRequest(UpdateEmployeeRequest request, @MappingTarget Employee employee);

    public abstract PagedResponse<EmployeeDto> toPagedDtos(Page<Employee> employees);
    
    protected Department mapDepartment(long departmentId) {
    	
    	return departmentService.findById(departmentId);
	}

    @Mapping(target = "employeeRecords", ignore = true)
	public abstract Employee clone(Employee employee);
}
