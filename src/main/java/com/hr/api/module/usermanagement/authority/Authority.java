package com.hr.api.module.usermanagement.authority;


import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;

import com.hr.api.module.usermanagement.permission.Permission;
import com.hr.api.module.usermanagement.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "authorities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority implements GrantedAuthority {

	@Transient
	public static final String ROLE_PREFIX = "ROLE_";

	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; 

    @ManyToMany(mappedBy = "roles")
    private Set<User>  users  = new HashSet<>();

    
 
    @ManyToMany
    @JoinTable(
        name = "authority_permissions",
        joinColumns = @JoinColumn(name = "authority_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();
    
    
	@Override
	public String getAuthority() {
		
		return this.name;
	}
	
	
	public static Authority getInstance(Long id) {
			
		return Authority.builder().id(id).build();
	}
	
	public static Authority getInstance(String name) {
		
		
		return Authority.builder().name(adjustAuthorityName(name)).build();
	}
	
	public static Authority getInstance(String name, Set<User> users) {
		
		return Authority.builder().name(adjustAuthorityName(name)).users(users).build();
	}
	
	
	public static String adjustAuthorityName(String name) {
		
		if(!name.toUpperCase().startsWith(ROLE_PREFIX)) {
			return ROLE_PREFIX + name.toUpperCase(); 
		}
		
		return name.toUpperCase();
	}
}
