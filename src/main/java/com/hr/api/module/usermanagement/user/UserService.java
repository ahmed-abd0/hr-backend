package com.hr.api.module.usermanagement.user;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hr.api.common.response.PagedResponse;
import com.hr.api.common.util.FileUtil;
import com.hr.api.module.usermanagement.auth.contract.UserAuthService;
import com.hr.api.module.usermanagement.auth.request.RegisterRequest;
import com.hr.api.module.usermanagement.authority.Authority;
import com.hr.api.module.usermanagement.permission.Permission;
import com.hr.api.module.usermanagement.user.contract.AuthorityUserService;
import com.hr.api.module.usermanagement.user.dto.UserDto;
import com.hr.api.module.usermanagement.user.dto.UserProfileDto;
import com.hr.api.module.usermanagement.user.request.CreateUserRequest;
import com.hr.api.module.usermanagement.user.request.UpdateUserRequest;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserAuthService{

	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final UserMapper userMapper;

	private final AuthorityUserService authorityService;

	private static final String UPLOAD_LOCATION = "uploads/profile/";
	
	public PagedResponse<UserDto> getAllUsers(Pageable pageable) {

		return userMapper.toPagedDtos(userRepository.findAll(pageable));
	}
	
	public Optional<UserDto> getUserById(Long id) {
		
	    return userRepository.findById(id).map(userMapper::toDto);
	}
	
	
	public UserDto updateUser(Long id,  UpdateUserRequest dto) {
		
		User user = userRepository.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        userMapper.updateUserFromDto(user, dto);
        
        return userMapper.toDto(userRepository.save(user));
	}
	
	@Transactional
	public UserDto updateUser(Long id,UpdateUserRequest dto, Optional<MultipartFile> file) {
		  
		UserDto user = updateUser(id, dto);
		
		if(file.isPresent()) {			
			user.setProfileImage(storeProfilePicture(user.getId(), file.get()));
		}
		
		return user;  
	}
	
	
	public UserDto createUser(CreateUserRequest dto) {
	   
		User user = userMapper.toEntity(dto);
	    
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
			
	    return userMapper.toDto(userRepository.save(user));
	}
	
	
	@Transactional
	public UserDto createUser(CreateUserRequest dto, Optional<MultipartFile> file) {
		  
		UserDto user = createUser(dto);
		
		if(file.isPresent()) {			
			user.setProfileImage(storeProfilePicture(user.getId(), file.get()));
		}
		
		return user;  
	}
	
	
	public void deleteUser(Long id) {
	   
		userRepository.deleteById(id);
    }
	

	@Transactional
	public User register(RegisterRequest registerUserDto) {
		
		User user = this.userMapper.toEntity(registerUserDto);
		
		user.setPassword(passwordEncoder.encode(registerUserDto.password()));
		
		this.loadRolesFromDatabase(user);
		
		user = this.userRepository.save(user);
				
		return user;
	}
	
	
	@Transactional
	public User findOrCreateOAuthUser(Map<String, String> oauthUser) {
		
		return this.userRepository.findByEmail(oauthUser.get("email")).orElseGet(() -> {
			
					
			User user = User.builder()
					.name(oauthUser.get("name"))
					.email(oauthUser.get("email"))
					.password(this.passwordEncoder.encode(UUID.randomUUID().toString().replace("-", "").substring(0, 12)))
					.roles(Set.of(this.authorityService.findByName("ROLE_USER")))
					.enabled(true)
					.build();	
						
			this.userRepository.save(user);
			
			return user;
		});
	}
	
	

	public UserDto getDtoByEmail(String email) {

		return this.userMapper.toDto(userRepository.findByEmail(email).get());
	}
	
	
	public UserProfileDto getProfileDtoByEmail(String email) {

		return this.userMapper.toProfileDto(userRepository.findByEmail(email).get());
	}
	
	


	public Optional<User> getByEmail(String email) {

		return userRepository.findByEmail(email);
	}

	
	public void assignAuthority(Long userId, Long authorityId) {

		
		Authority authority = this.authorityService.findById(authorityId);
				
		User user  = this.userRepository.findById(userId).get();
	
		user.getRoles().add(authority);
	
		this.userRepository.save(user);
	}
	
	
	private void loadRolesFromDatabase(User user) {
		
		Set<Authority> roles  = user.getRoles()
				.stream()
				.map(role -> authorityService.findById(role.getId()))
				.collect(Collectors.toSet());

		user.setRoles(roles);
		
	}

	public boolean hasPermission(User user, String permission) {

		Set<Authority> roles = user.getRoles();
		
		return roles.stream().anyMatch(role -> {
			
			Set<Permission> permissions = role.getPermissions();
			
			return permissions.stream().anyMatch(permissionObj -> permissionObj.getName().equals(permission));
		});
		
	}

	public String storeProfilePicture(Long userId, MultipartFile file) {
		
	
		User user = userRepository.findById(userId)
	              .orElseThrow(() -> new RuntimeException("User not found"));

        FileUtil.uploadImage(UPLOAD_LOCATION, file,  imageUrl -> {
        	
        	FileUtil.deleteFile(user.getProfileImage());
        	user.setProfileImage(imageUrl);
        	userRepository.save(user);
        });

        return user.getProfileImage();
	}

	
	
}



