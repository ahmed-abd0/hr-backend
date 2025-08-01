package com.hr.api.module.employeemanagment.department;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.api.common.response.PagedResponse;
import com.hr.api.module.employeemanagment.department.dto.DepartmentDto;
import com.hr.api.module.employeemanagment.department.dto.DepartmentSlimDto;
import com.hr.api.module.employeemanagment.department.request.CreateDepartmentRequest;
import com.hr.api.module.employeemanagment.department.request.UpdateDepartmentRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentSerivce;
    
    
    @GetMapping
//    @PreAuthorize("@permissionEvaluator.hasPermission('SHOW_DEPARTMENT')")
    public ResponseEntity<PagedResponse<DepartmentSlimDto>> getAll(@PageableDefault Pageable pageable) {
       
    	return ResponseEntity.ok(departmentSerivce.getAll(pageable));
    }

    @GetMapping("/{id}")
//    @PreAuthorize("@permissionEvaluator.hasPermission('SHOW_DEPARTMENT')")
    public ResponseEntity<DepartmentDto> getById(@PathVariable Long id) {
       
    	return ResponseEntity.ok(departmentSerivce.getById(id));
    }

    @PostMapping
//    @PreAuthorize("@permissionEvaluator.hasPermission('CREATE_DEPARTMENT')")
    public ResponseEntity<DepartmentDto> create(@RequestBody @Valid CreateDepartmentRequest request) {
      
    	return ResponseEntity.ok(departmentSerivce.create(request));
    }

    @PutMapping("/{id}")
//    @PreAuthorize("@permissionEvaluator.hasPermission('EDIT_DEPARTMENT')")
    public ResponseEntity<DepartmentDto> update(@PathVariable Long id, @RequestBody @Valid UpdateDepartmentRequest request) {
       
    	return ResponseEntity.ok(departmentSerivce.update(id, request));
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("@permissionEvaluator.hasPermission('DELETE_DEPARTMENT')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
      
    	departmentSerivce.delete(id);
        return ResponseEntity.noContent().build();
    }


}
