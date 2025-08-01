package com.hr.api.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PersistenceConfig {

    @Bean
    AuditorAware<String> auditorProvider() {
    
		return () -> {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	       
			if (auth != null && auth.isAuthenticated()) {
	            return Optional.ofNullable(auth.getName());
	        }
	        return Optional.of("SYSTEM");
		};
    }
}