package com.hr.api.module.usermanagement.permission;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.api.common.response.PagedResponse;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/permission")
public class PermissionController {

	private final PermissionService permissionService;
	
	@GetMapping
	public ResponseEntity<PagedResponse<PermissionDto>> getAllPermission(@RequestParam Optional<String> search, @PageableDefault Pageable pageable) {
		
		
		 return search.isEmpty() ? ResponseEntity.ok(permissionService.getAll(pageable)) : ResponseEntity.ok(permissionService.search(search.get(), pageable)); 
	}
	
}
