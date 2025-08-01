package com.hr.api.common.listeners;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Stream;

import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.hr.api.multitenancy.event.TenantCreatedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TenantEventListener {

	private final PasswordEncoder passwordEncoder;

	@EventListener
    public void handleTenantCreated(TenantCreatedEvent event) {
        		
		
		try(
				Connection connection = event.getDataSource().getConnection();
				Statement statement = connection.createStatement();
		) {
			
			String tenantInitSql = this.loadSqlScript("sql/tenant_init.sql", passwordEncoder.encode(event.getTenantData().getPassword()));
			
			for (String sql : tenantInitSql.split(";")) {
	            if (!sql.trim().isEmpty()) {
	          	    statement.execute(sql);
	            }
	        }	
			
			
		}catch (SQLException |IOException ex) {	
			log.error(ex.getMessage(), ex);	
		}
		
    }
	
	
	private String loadSqlScript(String path, String passwordHash) throws IOException {
	    String sql = new String(getClass().getClassLoader().getResourceAsStream(path).readAllBytes());
	    return sql.replace(":password", passwordHash );
	}


}
