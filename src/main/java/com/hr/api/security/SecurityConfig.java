package com.hr.api.security;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hr.api.module.usermanagement.user.User;
import com.hr.api.module.usermanagement.user.UserRepository;
import com.hr.api.multitenancy.filter.TenantHeaderFilter;
import com.hr.api.multitenancy.filter.TenantSubdomainFilter;
import com.hr.api.security.oauth.OAuth2SuccessHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;




@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

	private final DataSource dataSource;

	private final UserRepository userRepository;
	
	private final  AuthTokenFilter authTokenFilter;
    
	private final AuthEntryPointJwt unauthorizedHandler;
        
	private final OAuth2SuccessHandler oAuth2SuccessHandler;
			
	private final TenantHeaderFilter tenantHeaderFilter;
    
	@Value("${spring.security.oauth2.enabled}")
	private  boolean enableOAuth; 
	
	@Bean
	SecurityFilterChain filetChain(HttpSecurity http) throws Exception  {
		
		
		 http.authorizeHttpRequests((request) -> 
			request.requestMatchers( 
					"/auth/**",
					"/error"
	                ).permitAll()
					.requestMatchers("/uploads/**").permitAll()
					.anyRequest().authenticated()
		)
		.csrf(csrf -> csrf.disable())
		.exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.userDetailsService(this.userDetailsService())
		.addFilterBefore(tenantHeaderFilter, UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
		 
	
		 if(this.enableOAuth == true) {
			 http.oauth2Login(oauth2 -> oauth2.successHandler(oAuth2SuccessHandler));
		 }
		 
		 return http.build();
	
	}
	
	
	
	@Bean
	public UserDetailsService userDetailsService() {

		JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(this.dataSource) {
			@Override
			public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
				  User user = userRepository.findByEmail(email)
			                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
			      
				  List<SimpleGrantedAuthority> authorities = user.getAuthorities().stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).toList();
				  
			        return new org.springframework.security.core.userdetails.User(
			                user.getEmail(),
			                user.getPassword(),
			                authorities
			        );
			}
		};
		  
		return userDetailsManager;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
	
}
