package com.hr.api.multitenancy.tenantmanagment;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TenantMapper {
	
	DataSourceConfig toEntity(CreateTenantRequest dto);

}
