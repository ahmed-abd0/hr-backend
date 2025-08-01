package com.hr.api.module.usermanagement.permission;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import com.hr.api.common.response.PagedResponse;

@Mapper(componentModel = "spring")
public abstract class PermissionMapper {

	public abstract PermissionDto toDto(Permission permission);

	public abstract List<PermissionDto> toDtos(List<Permission> permission);


	public abstract Permission toEntity(PermissionDto permission);

	public abstract PagedResponse<PermissionDto> toPagedDtos(Page<Permission> permissions);
}
