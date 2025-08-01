package com.hr.api.multitenancy.tenantmanagment;

import com.hr.api.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public  class DataSourceConfig extends BaseEntity {


	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	private String url;
	
	private String username;
	
	private String password;
	
	private String tenantId;
		
	@Column(nullable = false)
	private String driverClassName = "com.mysql.cj.jdbc.Driver";
	
	@Column(nullable = false)
	private boolean initialized = false;
		
	
	public void setDriverClassName(String driverClassName) {
        this.driverClassName = (driverClassName == null || driverClassName.isBlank())
                ? "com.mysql.cj.jdbc.Driver"
                : driverClassName;
    }
}
