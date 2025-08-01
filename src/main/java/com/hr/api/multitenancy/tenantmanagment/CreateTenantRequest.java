package com.hr.api.multitenancy.tenantmanagment;


import com.hr.api.common.annotation.Unique;

import lombok.Data;

@Data
public class CreateTenantRequest {
	
	@Unique(entity = DataSourceConfig.class, fieldName = "tenantId")
	private String tenantId;
    private String url;
    private String username;
    private String password;
    private String driverClassName; 
}
