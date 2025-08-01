package com.hr.api.module.usermanagement.user;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.hr.api.common.response.PagedResponse;
import com.hr.api.module.usermanagement.auth.request.RegisterRequest;
import com.hr.api.module.usermanagement.authority.Authority;
import com.hr.api.module.usermanagement.user.contract.AuthorityUserService;
import com.hr.api.module.usermanagement.user.dto.UserDto;
import com.hr.api.module.usermanagement.user.dto.UserProfileDto;
import com.hr.api.module.usermanagement.user.helper.PermissionMapperHelper;
import com.hr.api.module.usermanagement.user.request.CreateUserRequest;
import com.hr.api.module.usermanagement.user.request.UpdateUserRequest;



@Mapper(componentModel = "spring", uses = PermissionMapperHelper.class)
public abstract class UserMapper {
	
	@Autowired
	private AuthorityUserService authorityService;


	@Mapping(target = "employeeId", source = "employee.id")
    public abstract UserDto toDto(User user);

    @Mapping(target = "password", ignore = true) 
    public abstract User toEntity(UserDto dto);
    
    @Mapping(target = "password", ignore = true) 
    public abstract User toEntity(CreateUserRequest dto);
    
    @Mapping(target = "password", ignore = true)
    public abstract User toEntity(RegisterRequest registerUserDto);
    
    @Mapping(target = "password", ignore = true)
    public abstract List<UserDto> toDtos(List<User> user);
    
    @Mapping(target = "permissions", expression = "java(com.hr.api.module.usermanagement.user.helper.PermissionMapperHelper.extractPermissionNames(user))")
    public abstract UserProfileDto toProfileDto(User user);

    public abstract PagedResponse<UserDto> toPagedDtos(Page<User> user);
    
    @Mapping(target = "password", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateUserFromDto(@MappingTarget User user, UpdateUserRequest userDto);
    
    
    protected List<String> mapAuthorities(Set<Authority> authorities) {
    	
    	if (authorities == null) return null;
    	
    	return authorities.stream().map(role -> role.getName()).toList();
    }
    
    
    protected Set<Authority> mapRoles(List<Long> roles) {
    	
    	if (roles == null) return null;
    	
    	Set<Long> rolesSet = new HashSet<>(roles);
    		
    	return rolesSet.stream().map(roleId ->
			    			authorityService.existsById(roleId) ? authorityService.findById(roleId) : authorityService.getInstance(roleId)
			    		).collect(Collectors.toSet());
    }



}
