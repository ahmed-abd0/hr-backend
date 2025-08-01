package com.hr.api.module.usermanagement.authority;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.api.common.response.PagedResponse;
import com.hr.api.module.usermanagement.authority.request.CreateAuthorityRequest;
import com.hr.api.module.usermanagement.permission.Permission;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authority")
public class AuthorityController {
	
	private final AuthorityService authorityService;

	
	@GetMapping
    @PreAuthorize("@permissionEvaluator.hasPermission('SHOW_AUTHORITY')")
	public ResponseEntity<PagedResponse<AuthorityDto>> getAllAuthorities(@PageableDefault Pageable pageable) {
		
		return ResponseEntity.ok(this.authorityService.findAllDtos(pageable));
	}
	
    @PostMapping
    @PreAuthorize("@permissionEvaluator.hasPermission('CREATE_AUTHORITY')")
    public ResponseEntity<AuthorityDto> create(@Valid @RequestBody CreateAuthorityRequest request) {
       
        return ResponseEntity.ok(authorityService.createAuthority(request));
    }

    
    @GetMapping("{id}/permissions")
    public ResponseEntity<Set<Permission>> getPermissions(@PathVariable long id) {
      
        return ResponseEntity.ok(authorityService.getPermissionsForAuthority(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@permissionEvaluator.hasPermission('DELETE_AUTHORITY')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        
    	authorityService.deleteAuthority(id);
        return ResponseEntity.noContent().build();
    }
    
  
   
   
}
