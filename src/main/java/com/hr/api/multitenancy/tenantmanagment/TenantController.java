package com.hr.api.multitenancy.tenantmanagment;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/admin/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @GetMapping
    public ResponseEntity<List<DataSourceConfig>> getAllTenants() {
		
    	return ResponseEntity.ok(this.tenantService.findAll());	
	}
    
    @PostMapping
    public ResponseEntity<DataSourceConfig> createTenant(@Valid @RequestBody CreateTenantRequest request) {
        DataSourceConfig created = tenantService.createTenant(request);
        return ResponseEntity.ok(created);
    }
    
    
    @GetMapping("init/{tenantId}")
    public ResponseEntity<?> getMethodName(@PathVariable long tenantId) {
        
    	this.tenantService.initTenantDatabase(tenantId);
    	
    	return ResponseEntity.ok(true);
    }
    
    
    @DeleteMapping("/{tenantId}")
    public ResponseEntity<?> delete(@PathVariable long tenantId) {
       
    	this.tenantService.deleteTenant(tenantId);
    	
    	return ResponseEntity.ok(true);
    }
    
    
    
}