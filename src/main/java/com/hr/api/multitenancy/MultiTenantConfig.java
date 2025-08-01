package com.hr.api.multitenancy;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableTransactionManagement
@RequiredArgsConstructor
public class MultiTenantConfig  {
    
   private final DataSourceProperties properties;
    
    @Bean
    DataSource dataSource() {

    	MultiTenantDataSource multiTenantDataSource = new MultiTenantDataSource();
    	Map<Object, Object> dataSources = new HashMap<>();

    	dataSources.put("default", createDefaultDataSource());
    	
    	multiTenantDataSource.setTargetDataSources(dataSources);
    	multiTenantDataSource.setDefaultTargetDataSource(dataSources.get("default"));
    	
    	return multiTenantDataSource;
    
    }


    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                        SchemaTenantIdentifierResolver tenantResolver) {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.multiTenancy", "DATABASE"); 
        properties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantResolver);
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        properties.put(Environment.HBM2DDL_AUTO, "update");

        entityManager.setJpaPropertyMap(properties);
        entityManager.setPackagesToScan("com.hr.api.module", "com.hr.api.common.entity", "com.hr.api.multitenancy");

        return entityManager;
    }

    @Bean
    SchemaTenantIdentifierResolver tenantResolver() {
        return new SchemaTenantIdentifierResolver();
    }

    @Bean
    PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
    
    
	 private DataSource createDefaultDataSource() {
	        
	    	HikariDataSource dataSource = new HikariDataSource();
	    	dataSource.setJdbcUrl(properties.getUrl());
	    	dataSource.setUsername(properties.getUsername());
	    	dataSource.setPassword(properties.getPassword());
	    	dataSource.setDriverClassName(properties.getDriverClassName());
	        
	        return dataSource;
	  }
    
    
    
}
