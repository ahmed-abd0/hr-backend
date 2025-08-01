package com.hr.api.module.employeemanagment.employeerecord;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.api.module.employeemanagment.employeerecord.dto.EmployeeRecordDto;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class EmployeeRecordController {

	private final EmployeeRecordService employeeRecordService;
	

    @GetMapping("/employees/{id}/records")
    public ResponseEntity<List<EmployeeRecordDto>> getEmployeeRecords(@PathVariable Long id) {
//    	
    	return ResponseEntity.ok(employeeRecordService.getEmployeeRecords(id));
    }
    
}
