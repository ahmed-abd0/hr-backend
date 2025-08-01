package com.hr.api.module.usermanagement.permission;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
	
    Optional<Permission> findByName(String name);
    
    boolean existsById(Long id);
    
    Page<Permission> findByNameContainingIgnoreCase(String ssearch, Pageable pageable);
    
    List<Permission> findByNameContainingAllIgnoreCase(String ssearch);
}
