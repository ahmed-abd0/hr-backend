package com.hr.api.module.usermanagement.user;


import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hr.api.common.response.PagedResponse;
import com.hr.api.module.usermanagement.user.dto.UserDto;
import com.hr.api.module.usermanagement.user.dto.UserProfileDto;
import com.hr.api.module.usermanagement.user.request.AssignAuthorityRequest;
import com.hr.api.module.usermanagement.user.request.CreateUserRequest;
import com.hr.api.module.usermanagement.user.request.UpdateUserRequest;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("@permissionEvaluator.hasPermission('SHOW_USER')")
    public ResponseEntity<PagedResponse<UserDto>> getAllUsers(@PageableDefault Pageable pageable) {
        
    	return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@permissionEvaluator.hasPermission('SHOW_USER')")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
       
    	return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("@permissionEvaluator.hasPermission('CREATE_USER')")
    public ResponseEntity<UserDto> createUser(@Valid @RequestPart("user") CreateUserRequest userDto, @RequestPart("profilePic") Optional<MultipartFile> file) {
      
    	return ResponseEntity.ok(userService.createUser(userDto, file));
    }
    
    
    @PutMapping("/{id}")
    @PreAuthorize("@permissionEvaluator.hasPermission('EDIT_USER')")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestPart("user") UpdateUserRequest userDto, @RequestPart("profilePic") Optional<MultipartFile> file) {
     
        return ResponseEntity.ok(userService.updateUser(id, userDto, file));
    }

    
    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> profile(@AuthenticationPrincipal UserDetails userDetails) {
		
    	return ResponseEntity.ok(this.userService.getProfileDtoByEmail(userDetails.getUsername()));
	}
    

    @DeleteMapping("/{id}")
    @PreAuthorize("@permissionEvaluator.hasPermission('DELETE_USER')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
       
    	userService.deleteUser(id);
    	
        return ResponseEntity.noContent().build();
    }
    
    
    @PostMapping("/assign-authority")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Void> assignToUser(@RequestBody AssignAuthorityRequest request) {
        
    	userService.assignAuthority(request.getUserId(), request.getAuthorityId());
    	
        return ResponseEntity.ok().build();
    }
    
    
    @PostMapping("/{id}/upload-profile-picture")
    public ResponseEntity<String> uploadProfilePicture(@PathVariable Long id,  @RequestParam MultipartFile file) {
   
        return ResponseEntity.ok(userService.storeProfilePicture(id, file));
    }
    
} 
