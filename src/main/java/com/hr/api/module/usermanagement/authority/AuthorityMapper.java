package com.hr.api.module.usermanagement.authority;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.hr.api.common.response.PagedResponse;
import com.hr.api.module.usermanagement.authority.contract.PermissionAuthorityService;
import com.hr.api.module.usermanagement.authority.request.CreateAuthorityRequest;
import com.hr.api.module.usermanagement.permission.Permission;


@Mapper(componentModel = "spring")
public abstract class AuthorityMapper {

	@Autowired
	private PermissionAuthorityService permissionService;
	
 	public abstract AuthorityDto toDto(Authority authority);
 	
 	@Mapping(source = "name", target = "name", qualifiedByName = "mapName")
 	public abstract Authority toEntity(CreateAuthorityRequest authority);
 	
 	@Mapping(source = "name", target = "name", qualifiedByName = "mapName")
 	public abstract Authority toEntity(AuthorityDto authority);

	public abstract List<AuthorityDto> toDtos(List<Authority> authorities);

	public abstract PagedResponse<AuthorityDto> toPagedDtos(Page<Authority> authorities);

	
	@Named("mapName")
	protected String mapName(String name) {
	
		return Authority.adjustAuthorityName(name);
	}

 	
	protected Set<Permission> mapPermissions(Set<Long> permissions) {
	    	
    	if (permissions == null) return null;
    	
    	Set<Long> permissionSet = new HashSet<>(permissions);
    		
    	return permissionSet.stream().map(permissionId ->
    						permissionService.existsById(permissionId) ? permissionService.findById(permissionId) : permissionService.getInstance(permissionId)
			    		).collect(Collectors.toSet());
	 }
}
