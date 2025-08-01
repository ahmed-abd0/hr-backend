package com.hr.api.multitenancy;


import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class MultiTenantDataSource extends AbstractRoutingDataSource {


	private final Map<Object, Object> dataSources = new HashMap<>();

	
	@Override
	protected Object determineCurrentLookupKey() {
		
		return TenantContext.getTenantId();
	}
	

	
   public void addDataSource(String tenantId, DataSource dataSource) {
        
	    this.dataSources.put(tenantId, dataSource);
     
	    super.setTargetDataSources(new HashMap<>(this.dataSources));
	    super.afterPropertiesSet();
    }
   
}
