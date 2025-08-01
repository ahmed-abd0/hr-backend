package com.hr.api.module.usermanagement.user;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import com.hr.api.module.usermanagement.auth.request.RegisterRequest;
import com.hr.api.module.usermanagement.authority.Authority;
import com.hr.api.module.usermanagement.user.contract.AuthorityUserService;
import com.hr.api.module.usermanagement.user.dto.UserDto;
import com.hr.api.module.usermanagement.user.request.CreateUserRequest;
import com.hr.api.module.usermanagement.user.request.UpdateUserRequest;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthorityUserService authorityService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
//        List<User> users = List.of(new User(), new User());
//        when(userRepository.findAll()).thenReturn(users);
//        when(userMapper.toDtos(users)).thenReturn(List.of(new UserDto(), new UserDto()));
//
//        List<UserDto> result = userService.getAllUsers();
//
//        assertEquals(2, result.size());
    }

    @Test
    void testGetUserByIdFound() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(new UserDto());

        Optional<UserDto> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
    }

    @Test
    void testGetUserByIdNotFound() {
    	
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<UserDto> result = userService.getUserById(1L);
        assertFalse(result.isPresent());
    }

    @Test
    void testUpdateUserSuccess() {
    	
        UpdateUserRequest dto =  UpdateUserRequest.builder().build(); 
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(new UserDto());

        UserDto result = userService.updateUser(1L, dto);

        assertNotNull(result);
    }

    @Test
    void testUpdateUserNotFound() {
    	
        UpdateUserRequest dto = UpdateUserRequest.builder().build();
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.updateUser(1L, dto));
    }

    @Test
    void testCreateUser() {
        CreateUserRequest dto = CreateUserRequest.builder().password("pass").build();

        User user = new User();
        user.setRoles(new HashSet<>(List.of(Authority.builder().name("ROLE_USER").build())));

        when(userMapper.toEntity(dto)).thenReturn(user);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPass");
        when(authorityService.adjustAuthorityName(anyString())).thenReturn("ROLE_USER");
        when(authorityService.findByName("ROLE_USER")).thenReturn(Authority.builder().name("ROLE_USER").build());
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(new UserDto());

        UserDto result = userService.createUser(dto);
        assertNotNull(result);
    }

    @Test
    void testRegister() {
        RegisterRequest dto = new RegisterRequest("John", "pass", null, true ,"ahmed@email.com" , List.of("ROLE_USER"));
       
        User user = new User();
        user.setRoles(new HashSet<>(List.of(Authority.builder().name("ROLE_USER").build())));

        when(userMapper.toEntity(dto)).thenReturn(user);
        when(passwordEncoder.encode(dto.password())).thenReturn("encodedPass");
        when(authorityService.findByName("ROLE_USER")).thenReturn(Authority.builder().name("ROLE_USER").build());
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.register(dto);
        assertNotNull(result);
        assertEquals("encodedPass", result.getPassword());
    }
} 