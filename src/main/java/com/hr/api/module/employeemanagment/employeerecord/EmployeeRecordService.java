package com.hr.api.module.employeemanagment.employeerecord;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hr.api.common.response.PagedResponse;
import com.hr.api.module.employeemanagment.employee.Employee;
import com.hr.api.module.employeemanagment.employeerecord.dto.EmployeeRecordDto;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmployeeRecordService {

    private final EmployeeRecordRepository employeeRecordRepository;

    private final EmployeeRecordMapper employeeRecordMapper;
    
    public EmployeeRecord save(EmployeeRecord record) {
        return employeeRecordRepository.save(record);
    }

    public List<EmployeeRecord> findByEmployee(Employee employee) {
    	
        return employeeRecordRepository.findByEmployee(employee);
    }

    public List<EmployeeRecord> findAll() {
    	
        return employeeRecordRepository.findAll();
    }

    public EmployeeRecord findById(Long id) {
    	
        return employeeRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
    }
    
    
    public PagedResponse<EmployeeRecordDto> getEmployeeRecords(Long id, Pageable pageable) {

		return employeeRecordMapper.toPagedDto(this.employeeRecordRepository.findByEmployeeId(id, pageable));
	}

}
