package com.gustavosass.orders.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavosass.orders.config.ApplicationConfig;
import com.gustavosass.orders.security.SecurityConfiguration;
import com.gustavosass.orders.enums.RoleEnum;
import com.gustavosass.orders.security.JwtAuthenticationFilter;
import com.gustavosass.orders.security.JwtService;
import com.gustavosass.orders.service.UserService;
import com.gustavosass.orders.mapper.UserMapper;
import com.gustavosass.orders.model.user.User;
import com.gustavosass.orders.model.user.dto.PasswordDTO;
import com.gustavosass.orders.model.user.dto.RegisterDTO;
import com.gustavosass.orders.model.user.dto.UserDTO;
import com.gustavosass.orders.repository.UserRepository;

@WebMvcTest(UserController.class)
@Import({SecurityConfiguration.class, JwtAuthenticationFilter.class, ApplicationConfig.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserRepository userRepository;
    
    @MockBean
    private JwtService jwtService;

    private User user;
    private UserDTO userDTO;
    private RegisterDTO registerDTO;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("Test User")
                .email("test@test.com")
                .password("password")
                .role(RoleEnum.USER)
                .build();

        userDTO = new UserDTO(1L, "Test User", "test@test.com", RoleEnum.USER);
        registerDTO = new RegisterDTO("Test User", "test@test.com", "password", RoleEnum.USER);

        // Mock token generation for all tests that may require it
        when(jwtService.generateToken(any())).thenReturn("mocked-jwt-token");
        when(jwtService.generateRefreshToken(any())).thenReturn("mocked-refresh-token");
        when(jwtService.isTokenValid(any(), any())).thenReturn(true);
        when(jwtService.extractUsername(any())).thenReturn("test@test.com");
    }

    @Test
    @WithMockUser
    @DisplayName("Deve retornar usuário por ID")
    void whenGetUserByIdThenReturnUser() throws Exception {
        when(userService.findById(1L)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);
        

        mockMvc.perform(
                    get("/api/v1/users/1")
                    .header("Authorization", "Bearer mocked-jwt-token")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    @WithMockUser
    @DisplayName("Deve retornar lista de usuários")
    void whenFindAllThenReturnUserList() throws Exception {
        when(userService.findAll()).thenReturn(List.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        mockMvc.perform(get("/api/v1/users")
                .header("Authorization", "Bearer mocked-jwt-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test User"))
                .andExpect(jsonPath("$[0].email").value("test@test.com"))
                .andExpect(jsonPath("$[0].role").value("USER"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    @DisplayName("Deve criar usuário com sucesso")
    void whenCreateUserThenReturnCreatedUser() throws Exception {
        when(userMapper.toEntity(registerDTO)).thenReturn(user);
        when(userService.create(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        mockMvc.perform(post("/api/v1/users")
                .header("Authorization", "Bearer mocked-jwt-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    @WithMockUser
    @DisplayName("Atualizar usuário com sucesso")
    void whenUpdateUserThenReturnUpdatedUser() throws Exception {
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(user);
        when(userService.update(1L, user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        mockMvc.perform(put("/api/v1/users/1")
                .header("Authorization", "Bearer mocked-jwt-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    @DisplayName("Deve deletar usuário com sucesso")
    void whenDeleteUserThenReturn204() throws Exception {
        doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/api/v1/users/1")
                .header("Authorization", "Bearer mocked-jwt-token"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    @DisplayName("Deve atualizar senha com sucesso")
    void whenUpdatePasswordThenReturn204() throws Exception {
        PasswordDTO passwordDTO = new PasswordDTO("newpassword");
        doNothing().when(userService).updatePassword(1L, "newpassword");

        mockMvc.perform(put("/api/v1/users/1/password")
                .header("Authorization", "Bearer mocked-jwt-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passwordDTO)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    @DisplayName("Deve retornar 403 quando usuário não autorizado tentar criar")
    void whenUnauthorizedUserTriesToCreateThenReturn403() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    @DisplayName("Deve retornar 403 quando usuário não autorizado tentar deletar")
    void whenUnauthorizedUserTriesToDeleteThenReturn403() throws Exception {
        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    @DisplayName("Deve retornar 403 quando usuário não autorizado tentar atualizar senha")
    void whenUnauthorizedUserTriesToUpdatePasswordThenReturn403() throws Exception {
        PasswordDTO passwordDTO = new PasswordDTO("newpassword");

        mockMvc.perform(put("/api/v1/users/1/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passwordDTO)))
                .andExpect(status().isUnauthorized());
    }
}