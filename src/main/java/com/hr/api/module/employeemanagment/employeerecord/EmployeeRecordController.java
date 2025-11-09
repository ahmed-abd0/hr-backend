package com.hr.api.module.employeemanagment.employeerecord;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.api.common.response.PagedResponse;
import com.hr.api.module.employeemanagment.employeerecord.dto.EmployeeRecordDto;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/records")
@RequiredArgsConstructor
@RestController
public class EmployeeRecordController {

	private final EmployeeRecordService employeeRecordService;
	

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<PagedResponse<EmployeeRecordDto>> getEmployeeRecords(@PathVariable Long employeeId, @PageableDefault Pageable pageable) {

    	return ResponseEntity.ok(employeeRecordService.getEmployeeRecords(employeeId, pageable));
    }
    
}
