package com.hr.api.module.employeemanagment.leave;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.api.common.response.PagedResponse;
import com.hr.api.module.employeemanagment.leave.dto.LeaveDto;
import com.hr.api.module.employeemanagment.leave.request.CreateLeaveRequest;
import com.hr.api.module.employeemanagment.leave.request.UpdateLeaveRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    @GetMapping 
    public ResponseEntity<PagedResponse<LeaveDto>> getAll(@PageableDefault Pageable pageable) {
    	
    	return ResponseEntity.ok(leaveService.getAll(pageable));
    }

   
    @PostMapping
    public ResponseEntity<LeaveDto> create(@RequestBody @Valid CreateLeaveRequest request) {
        return ResponseEntity.ok(leaveService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveDto> update(@PathVariable Long id, @RequestBody UpdateLeaveRequest request) {
        return ResponseEntity.ok(leaveService.update(id, request));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveDto>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(leaveService.getByEmployee(employeeId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

    	leaveService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
