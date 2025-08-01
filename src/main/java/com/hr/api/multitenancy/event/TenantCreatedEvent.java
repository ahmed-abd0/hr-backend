package com.hr.api.multitenancy.event;

import javax.sql.DataSource;

import com.hr.api.multitenancy.tenantmanagment.DataSourceConfig;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TenantCreatedEvent {

	private DataSourceConfig tenantData;
	
	private DataSource dataSource;
	
}
