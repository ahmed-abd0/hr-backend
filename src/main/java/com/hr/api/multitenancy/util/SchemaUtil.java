package com.hr.api.multitenancy.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.hr.api.module.usermanagement.user.UserRepository;
import com.hr.api.multitenancy.excption.DatabaseUsernameAlreadyExists;
import com.hr.api.multitenancy.tenantmanagment.DataSourceConfig;

import jakarta.persistence.EntityManager;


public class SchemaUtil {
     
	
	 public static void createDatabaseIfNotExists(DataSource dataSource, String schemaName) {
	        
		 try (Connection connection = dataSource.getConnection();
	             Statement stmt = connection.createStatement()) 
		 	{
			 
	            stmt.execute("CREATE DATABASE IF NOT EXISTS `" + schemaName + "`");
	            
	            
	        } catch (SQLException e) {
	        	
	            throw new RuntimeException("Failed to create database: " + schemaName, e);
	        }
	    }
	
		
		public static void generateSchemaForTenant(DataSource tenantDataSource) {
		    
			
			LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		   
			emf.setDataSource(tenantDataSource);
		    emf.setPackagesToScan("com.hr.api.module", "com.hr.api.common"); 
		    emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
	
		    Map<String, Object> props = new HashMap<>();
		    props.put("hibernate.hbm2ddl.auto", "update"); 
		    props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
	
		    emf.setJpaPropertyMap(props);
		    emf.afterPropertiesSet(); 
		    
		    emf.destroy();
		}
	
	
		public static void createTenantUser(DataSource dataSource, DataSourceConfig dataSourceConfig) {
			 
			try (Connection connection = dataSource.getConnection();
					Statement stmt = connection.createStatement()) 
				
			 	{
				 
			
					stmt.execute("CREATE USER IF NOT EXISTS  '" + dataSourceConfig.getUsername() +"'@'%' IDENTIFIED BY '" + dataSourceConfig.getPassword() +"';");
					
					stmt.execute("GRANT ALL PRIVILEGES ON " + dataSourceConfig.getTenantId() + ".* TO '"+ dataSourceConfig.getUsername() +"'@'%'");
					
					stmt.execute("FLUSH PRIVILEGES;");
					
		        } catch (SQLException e) {
		        			        	
		            throw new RuntimeException("Failed to create user: " + dataSourceConfig.getUsername(), e);
		        }
		}
		
		
		public static boolean userExists(DataSource dataSource, String username) {
			 
			try (Connection connection = dataSource.getConnection();
					PreparedStatement checkStmt = connection.prepareStatement("SELECT COUNT(*) FROM mysql.user WHERE user = ?"))
			{
				checkStmt.setString(1, username);
			 	
			 	try (ResultSet rs = checkStmt.executeQuery()) {
			 	    
		            return rs.next() && rs.getInt(1) > 0;
		        }
			 	
			} catch (SQLException e) {
	            
				throw new RuntimeException("Failed to create user: " + username, e);
			}
		}
		
		
		 public static void dropDatabaseAndUser(DataSource dataSource, DataSourceConfig dataSourceConfig)  {
		        
			 try (	Connection connection = dataSource.getConnection();
		        		Statement stmt = connection.createStatement()) {
		            
		            stmt.execute("REVOKE ALL PRIVILEGES, GRANT OPTION FROM `" + dataSourceConfig.getUsername() + "`@'%'");

		            stmt.execute("DROP USER IF EXISTS `" + dataSourceConfig.getUsername() + "`@'%'");

		            stmt.execute("DROP DATABASE IF EXISTS `" + dataSourceConfig.getTenantId() + "`");

		        } catch (SQLException ex) {
		        	
		            throw new RuntimeException("Failed to drop tenant DB or user: " + ex.getMessage(), ex);
		        }
		    }
		
}
