package com.hr.api.module.usermanagement.permission;


import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hr.api.module.usermanagement.authority.Authority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "permissions")
    private Set<Authority> authorities = new HashSet<>();

    
    public static Permission getInstance(String name) {
        return Permission.builder()
                .name(name.toUpperCase())
                .build();
    }
    
    public static Permission getInstance(String name, Set<Authority> authorities) {
        return Permission.builder()
                .name(name.toUpperCase())
                .authorities(authorities)
                .build();
    }
    
    
}






