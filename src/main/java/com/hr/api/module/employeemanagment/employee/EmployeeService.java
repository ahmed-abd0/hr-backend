package com.hr.api.module.employeemanagment.employee;

import java.lang.classfile.instruction.NewMultiArrayInstruction;
import java.util.Optional;
import java.util.Set;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hr.api.common.response.PagedResponse;
import com.hr.api.common.util.FileUtil;
import com.hr.api.common.util.RequestUtil;
import com.hr.api.module.employeemanagment.employee.dto.EmployeeDto;
import com.hr.api.module.employeemanagment.employee.event.EmployeeCreatedEvent;
import com.hr.api.module.employeemanagment.employee.event.EmployeeUpdatedEvent;
import com.hr.api.module.employeemanagment.employee.request.CreateEmployeeRequest;
import com.hr.api.module.employeemanagment.employee.request.UpdateEmployeeRequest;
import com.hr.api.module.employeemanagment.employeerecord.EmployeeRecord;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService  {


    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final RequestUtil requestUtil;
    
    private final static String CV_UPLOAD_LOCATION = "uploads/cv/"; 
    
    public PagedResponse<EmployeeDto> getAll(Pageable pageable) {
        
    	return employeeMapper.toPagedDtos(employeeRepository.findAll(pageable));
    }

    public EmployeeDto getById(Long id) {
        
    	Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        
        return employeeMapper.toDto(employee);
    }

    public EmployeeDto create(CreateEmployeeRequest createEmployeeRequest) {
        
    	Employee employee = employeeMapper.toEntity(createEmployeeRequest);
    	
    	employee = employeeRepository.save(employee);
    	
    	this.applicationEventPublisher.publishEvent(new EmployeeCreatedEvent(requestUtil.getCurrentUser(), employee));
    	
    	return employeeMapper.toDto(employee);
    }

    public EmployeeDto update(Long id, UpdateEmployeeRequest updateEmployeeRequest) {
        
    	Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    	
    	Employee employeeBeforeUpdate = employeeMapper.clone(employee);
        
        employeeMapper.updateEmployeeFromRequest(updateEmployeeRequest, employee);
        
        employee = employeeRepository.save(employee);
        

		log.error(requestUtil.getCurrentUser().getEmail());
		
      
        this.applicationEventPublisher.publishEvent(new EmployeeUpdatedEvent(requestUtil.getCurrentUser(), employeeBeforeUpdate, employee));
        
        return employeeMapper.toDto(employee);
    }

    @Transactional
    public EmployeeDto create(CreateEmployeeRequest createEmployeeRequest, Optional<MultipartFile> file) {
    	
    	EmployeeDto employee = create(createEmployeeRequest);
    	
    	if(file.isPresent()) {
    		employee.setCvUrl(storeCv(employee.getId(), file.get()));
    	}
    
    	return employee;
    }
    
    @Transactional
    public EmployeeDto update(Long id, UpdateEmployeeRequest updateEmployeeRequest, Optional<MultipartFile> file) {
        
    	EmployeeDto employee = update(id, updateEmployeeRequest);
    	    	
    	if(file.isPresent()) {
    		employee.setCvUrl(storeCv(employee.getId(), file.get()));
    	}
    
    	return employee;
    }
    
    public String storeCv(Long id, MultipartFile file) {
    	
    	Employee employee = employeeRepository.findById(id)
	              .orElseThrow(() -> new RuntimeException("Employee not found"));

	      FileUtil.uploadPdfOrWord(CV_UPLOAD_LOCATION, file,  url -> {
	    
		      	FileUtil.deleteFile(employee.getCvUrl());
		      	employee.setCvUrl(url);
		      	employeeRepository.save(employee);
	      });

	    return employee.getCvUrl();
    }
    
    
    public void delete(Long id) {
    	
    	employeeRepository.deleteById(id);
    }

	
    public Employee findById(Long employeeId) {
		
		return this.employeeRepository.findById(employeeId).get();
	}

	

}
