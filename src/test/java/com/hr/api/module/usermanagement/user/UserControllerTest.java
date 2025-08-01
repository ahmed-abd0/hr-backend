package com.hr.api.module.usermanagement.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hr.api.module.usermanagement.user.dto.UserDto;
import com.hr.api.module.usermanagement.user.request.CreateUserRequest;
import com.hr.api.module.usermanagement.user.request.UpdateUserRequest;


//@WebMvcTest(UserController.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private UserService userService;
    @Autowired private ObjectMapper objectMapper;
    
   
    private static final Long USER_ID = 1L;
    private static final String EMAIL = "user@example.com";
    private static final String NAME = "username";
    private static final List<String> ROLES = List.of("USER");

    private UserDto userDto;

    @BeforeEach
    void setup() {
        userDto = new UserDto(USER_ID, NAME, EMAIL, null, true, ROLES);
    }

    @Test
    @WithMockUser
    void testGetAllUsers() throws Exception {
//        Mockito.when(userService.getAllUsers()).thenReturn(List.of(userDto));
//
//        mockMvc.perform(get("/api/v1/users"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].email").value(EMAIL));
    }

    @Test
    @WithMockUser
    void testGetUserById_Found() throws Exception {
        Mockito.when(userService.getUserById(USER_ID)).thenReturn(Optional.of(userDto));

        mockMvc.perform(get("/api/v1/users/{id}", USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(EMAIL));
    }

    @Test
    @WithMockUser
    void testGetUserById_NotFound() throws Exception {
        Mockito.when(userService.getUserById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/users/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testCreateUser() throws Exception {
        CreateUserRequest req = CreateUserRequest.builder()
                .name(NAME).email(EMAIL).password("Password@123").enabled(true).roles(ROLES).build();

        Mockito.when(userService.createUser(Mockito.any())).thenReturn(userDto);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(EMAIL));
    }

    @Test
    @WithMockUser
    void testUpdateUser() throws Exception {
        UpdateUserRequest req = UpdateUserRequest.builder()
                .name(NAME).email(EMAIL).enabled(true).roles(ROLES).build();

        Mockito.when(userService.updateUser(Mockito.eq(USER_ID), Mockito.any())).thenReturn(userDto);

        mockMvc.perform(put("/api/v1/users/{id}", USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(EMAIL));
    }

    @Test
    @WithMockUser
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/v1/users/{id}", USER_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = EMAIL)
    void testGetProfile() throws Exception {
        Mockito.when(userService.getDtoByEmail(EMAIL)).thenReturn(userDto);

        mockMvc.perform(get("/api/v1/users/profile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(EMAIL));
    }
}
