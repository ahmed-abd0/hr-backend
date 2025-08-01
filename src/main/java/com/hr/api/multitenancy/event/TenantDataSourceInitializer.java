package com.hr.api.multitenancy.event;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.hr.api.multitenancy.MultiTenantDataSource;
import com.hr.api.multitenancy.tenantmanagment.DataSourceConfigRepository;
import com.hr.api.multitenancy.tenantmanagment.TenantService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TenantDataSourceInitializer  {


    private final DataSourceConfigRepository dataSourceConfigRepositry;
    private final MultiTenantDataSource multiTenantDataSource;
    private final TenantService tenantService;
    private final DataSourceProperties properties;
	

    @EventListener(ApplicationReadyEvent.class)
	public void onApplicationEvent(ApplicationReadyEvent event) {

    	multiTenantDataSource.addDataSource("default", tenantService.createDataSource(properties.getUrl(), properties));
       
    	this.setDataSroucesFromConfig();
    }
	
	
    private void setDataSroucesFromConfig() {
    	
    	
    	dataSourceConfigRepositry.findAll().stream().forEach(dataSource -> {
    		
    		multiTenantDataSource.addDataSource(dataSource.getTenantId(), tenantService.createDataSource(dataSource));
    	});
    	
    }

	
}
