package com.hr.api.module.employeemanagment.employee;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hr.api.common.response.PagedResponse;
import com.hr.api.module.employeemanagment.employee.dto.EmployeeDto;
import com.hr.api.module.employeemanagment.employee.request.CreateEmployeeRequest;
import com.hr.api.module.employeemanagment.employee.request.UpdateEmployeeRequest;
import com.hr.api.module.employeemanagment.employeerecord.EmployeeRecord;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<PagedResponse<EmployeeDto>> getAll(@PageableDefault Pageable pageable) {
     
    	return ResponseEntity.ok(employeeService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getById(@PathVariable Long id) {
    	
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EmployeeDto> create(@RequestPart("employee") @Valid CreateEmployeeRequest request, @RequestPart("cv") Optional<MultipartFile> file) {
       
    	return ResponseEntity.ok(employeeService.create(request, file));
    }

    @PutMapping(value =  "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EmployeeDto> update(@PathVariable Long id, @RequestPart("employee") @Valid UpdateEmployeeRequest request, @RequestPart("cv") Optional<MultipartFile> file) {
      
    	return ResponseEntity.ok(employeeService.update(id, request, file));
    }

   
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        
    	employeeService.delete(id);
    	return ResponseEntity.noContent().build();
    }
}
