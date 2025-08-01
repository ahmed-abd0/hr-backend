package com.hr.api.module.usermanagement.permission;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hr.api.common.response.PagedResponse;
import com.hr.api.module.usermanagement.authority.contract.PermissionAuthorityService;
import com.hr.api.module.usermanagement.permission.contract.AuthorityPermissionService;
import com.hr.api.module.usermanagement.permission.request.CreatePermissionRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionService implements PermissionAuthorityService {

    private final PermissionRepository permissionRepository;
    
    private final PermissionMapper permissionMapper;
    
    @Lazy
    @Autowired
    private AuthorityPermissionService authorityService;

    public PermissionDto create(CreatePermissionRequest request) {
    	
        Permission permission = Permission.getInstance(request.getName());
        
        return permissionMapper.toDto(permissionRepository.save(permission));
    }

    public PermissionDto getById(Long id) {
       
    	Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));
    	
        return permissionMapper.toDto(permission);
    }

    public PagedResponse<PermissionDto> getAll(Pageable pageable) {
       
    	return permissionMapper.toPagedDtos(permissionRepository.findAll(pageable));
    }
    
    public PagedResponse<PermissionDto> search(String search, Pageable pageable) {

    	return permissionMapper.toPagedDtos(permissionRepository.findByNameContainingIgnoreCase(search, pageable));
	}

	@Override
	public boolean existsById(Long permissionId) {
		return this.permissionRepository.existsById(permissionId);
	}

	@Override
	public Permission findById(Long permissionId) {
		
		return this.permissionRepository.findById(permissionId).get();
	}

	@Override
	public Permission getInstance(Long permissionId) {
		
		return Permission.builder().id(permissionId).build();
	}

	
}