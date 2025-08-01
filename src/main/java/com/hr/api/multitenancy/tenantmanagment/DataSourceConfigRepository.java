package com.hr.api.multitenancy.tenantmanagment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface DataSourceConfigRepository extends JpaRepository<DataSourceConfig, Long> {

	boolean existsByTenantId(String tenantId);
	
	DataSourceConfig findByTenantId(String tenatId);

	@Modifying
	@Query("UPDATE DataSourceConfig d SET d.initialized = true WHERE d.tenantId = :tenantId")
	@Transactional
	void markAsInitialized(@Param("tenantId") String tenantId);

}
