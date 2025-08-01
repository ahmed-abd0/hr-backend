package com.hr.api.multitenancy.tenantmanagment;

import java.util.List;

import javax.sql.DataSource;

import org.hibernate.boot.jaxb.mapping.marshall.LockModeTypeMarshalling;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.hr.api.multitenancy.MultiTenantDataSource;
import com.hr.api.multitenancy.event.TenantCreatedEvent;
import com.hr.api.multitenancy.excption.DatabaseUsernameAlreadyExists;
import com.hr.api.multitenancy.util.SchemaUtil;
import com.zaxxer.hikari.HikariDataSource;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TenantService {

	private final TenantMapper tenantMapper;
	private final DataSourceConfigRepository dataSourceConfigRepository;
	private final DataSource dataSource;
	private final MultiTenantDataSource multiTenantDataSource;
	private final ApplicationEventPublisher applicationEventPublisher;
	
	@Transactional
	public DataSourceConfig createTenant(CreateTenantRequest request) {
		
		if (dataSourceConfigRepository.existsByTenantId(request.getTenantId())) {
            throw new IllegalArgumentException("Tenant already exists");
        }
		
		
		DataSourceConfig dataSourceConfig = this.tenantMapper.toEntity(request);
		
		dataSourceConfig.setUrl(request.getUrl() + "/" + request.getTenantId());
		
		dataSourceConfig = this.dataSourceConfigRepository.save(dataSourceConfig);
		
		initTenantDatabase(dataSourceConfig);
		
		return dataSourceConfig;
	}
	
	
	public void initTenantDatabase(Long id) {
		
		DataSourceConfig dataSourceConfig = dataSourceConfigRepository.findById(id).get();
		
		initTenantDatabase(dataSourceConfig);
	}

	
	public void initTenantDatabase(DataSourceConfig dataSourceConfig) {
		
		this.handleTenantSchema(dataSourceConfig);
		
		multiTenantDataSource.addDataSource(dataSourceConfig.getTenantId(), createDataSource(dataSourceConfig));		
		
		this.applicationEventPublisher.publishEvent(new TenantCreatedEvent(dataSourceConfig, createDataSource(dataSourceConfig)));
		
		dataSourceConfigRepository.markAsInitialized(dataSourceConfig.getTenantId());
		
	}
	
	

	public  DataSource createDataSource(DataSourceConfig dataSourceConfig) {
        
    	HikariDataSource dataSource = new HikariDataSource();
    	dataSource.setJdbcUrl(dataSourceConfig.getUrl());	
    	dataSource.setUsername(dataSourceConfig.getUsername());
    	dataSource.setPassword(dataSourceConfig.getPassword());
    	dataSource.setDriverClassName(dataSourceConfig.getDriverClassName());
        
        return dataSource;
    }
   

	public  DataSource createDataSource(String url, DataSourceProperties properties) {
	        
	    	HikariDataSource dataSource = new HikariDataSource();
	    	dataSource.setJdbcUrl(url);
	    	dataSource.setUsername(properties.getUsername());
	    	dataSource.setPassword(properties.getPassword());
	    	dataSource.setDriverClassName(properties.getDriverClassName());
	        
	        return dataSource;
	  }
	
	
	
	@Transactional
	public void handleTenantSchema(DataSourceConfig dataSourceConfig) {
		
		if(SchemaUtil.userExists(dataSource, dataSourceConfig.getUsername())) {
            throw new DatabaseUsernameAlreadyExists("username", dataSourceConfig.getUsername());
		}
		
		SchemaUtil.createDatabaseIfNotExists(dataSource, dataSourceConfig.getTenantId());
		
		SchemaUtil.createTenantUser(dataSource, dataSourceConfig);
		
		SchemaUtil.generateSchemaForTenant(createDataSource(dataSourceConfig));
		
	}
	


	public List<DataSourceConfig> findAll() {
		
		return this.dataSourceConfigRepository.findAll();
	}

	
	@Transactional
	public void deleteTenant(long tenantId) {
		
		DataSourceConfig dataSourceConfig = dataSourceConfigRepository.findById(tenantId).get();
		
		SchemaUtil.dropDatabaseAndUser(dataSource, dataSourceConfig);
		
		this.dataSourceConfigRepository.delete(dataSourceConfig);
	}
	
	
}
