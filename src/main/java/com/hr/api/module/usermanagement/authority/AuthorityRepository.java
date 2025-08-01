package com.hr.api.module.usermanagement.authority;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

	Optional<Authority> findByName(String name);

	boolean existsByName(String name);
	
	@Modifying
	@Transactional
	@Query(
	    value = """
	        INSERT INTO user_roles(user_id, authority_id)
	        SELECT :userId, a.id FROM authorities a WHERE a.name = :authorityName
	        ON DUPLICATE KEY UPDATE authority_id = authority_id
	    """,
	    nativeQuery = true
	)
	void assignUserToAuthority(@Param("userId") Long userId, @Param("authorityName") String authorityName);

 
}
