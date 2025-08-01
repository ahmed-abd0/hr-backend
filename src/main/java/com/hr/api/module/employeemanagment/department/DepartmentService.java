package com.hr.api.module.employeemanagment.department;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hr.api.common.response.PagedResponse;
import com.hr.api.module.employeemanagment.department.dto.DepartmentDto;
import com.hr.api.module.employeemanagment.department.dto.DepartmentSlimDto;
import com.hr.api.module.employeemanagment.department.request.CreateDepartmentRequest;
import com.hr.api.module.employeemanagment.department.request.UpdateDepartmentRequest;
import com.hr.api.module.employeemanagment.employee.contract.DepartmentEmployeeService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService implements DepartmentEmployeeService {


    private final DepartmentRepository departmentRepository ;
    private final DepartmentMapper departmentMapper;

    
    public PagedResponse<DepartmentSlimDto> getAll(Pageable pageable) {
      
    	return departmentMapper.toPagedSlimDtos(departmentRepository.findAll(pageable));
    }

    
    public DepartmentDto getById(Long id) {
     
    	return departmentRepository .findById(id)
            .map(departmentMapper::toDto)
            .orElseThrow(() -> new EntityNotFoundException("Department not found"));
    }
    
    
    public Department findById(Long id) {
        
    	return departmentRepository .findById(id).get();
    }
    
    
    
    public DepartmentDto create(CreateDepartmentRequest request) {

        Department department = departmentMapper.toEntity(request);

        if (request.getParentId() != null) {
            
        	Department parent = departmentRepository.findById(request.getParentId())
                .orElseThrow(() -> new EntityNotFoundException("Parent not found"));
            
            department.setParent(parent);
        }

        return departmentMapper.toDto(departmentRepository .save(department));
    }

    
    public DepartmentDto update(Long id, UpdateDepartmentRequest request) {
      
    	Department department = departmentRepository .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Department not found"));

        departmentMapper.update(department, request);

        if (request.getParentId() != null && !request.getParentId().equals(id)) {
         
        	Department parent = departmentRepository.findById(request.getParentId())
                .orElseThrow(() -> new EntityNotFoundException("Parent not found"));
            
        	department.setParent(parent);
        }

        return departmentMapper.toDto(departmentRepository .save(department));
    }

    
    public void delete(Long id) {
        
    	departmentRepository.deleteById(id);
    }

 
}
