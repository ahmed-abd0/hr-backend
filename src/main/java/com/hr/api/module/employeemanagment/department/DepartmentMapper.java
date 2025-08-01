package com.hr.api.module.employeemanagment.department;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;

import com.hr.api.common.response.PagedResponse;
import com.hr.api.module.employeemanagment.department.dto.DepartmentDto;
import com.hr.api.module.employeemanagment.department.dto.DepartmentSlimDto;
import com.hr.api.module.employeemanagment.department.request.CreateDepartmentRequest;
import com.hr.api.module.employeemanagment.department.request.UpdateDepartmentRequest;
import com.hr.api.module.employeemanagment.employee.Employee;

@Mapper(componentModel = "spring")
public abstract class DepartmentMapper {

	@Autowired
	@Lazy
	private DepartmentService departmentService;
	
    public abstract DepartmentDto toDto(Department department);
   
    @Mapping(target = "parent", source = "parent.id")
    public abstract DepartmentSlimDto toSlimDto(Department department);
  

    public abstract List<DepartmentDto> toDtos(List<Department> departments);

    @Mapping(target = "parent", source = "parentId")
    public abstract Department toEntity(CreateDepartmentRequest request);

    
    @Mapping(target = "parent", source = "parentId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget Department department, UpdateDepartmentRequest request);

	public abstract PagedResponse<DepartmentDto> toPagedDtos(Page<Department> departments);
	
	public abstract PagedResponse<DepartmentSlimDto> toPagedSlimDtos(Page<Department> departments);
	
	
	protected  Set<Long> mapEmployeesToIds(Set<Employee> employees) {

		 if (employees == null) return null;
	       
	        return employees.stream()
	                        .map(Employee::getId)
	                        .collect(Collectors.toSet());
	 }
	
	protected Department mapDepartment(long departmentId) {
		
		return this.departmentService.findById(departmentId);
	}
}
