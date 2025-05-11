package com.gustavosass.orders.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavosass.orders.config.ApplicationConfig;
import com.gustavosass.orders.model.user.dto.AuthenticationRequestDTO;
import com.gustavosass.orders.model.user.dto.AuthenticationResponseDTO;
import com.gustavosass.orders.security.SecurityConfiguration;
import com.gustavosass.orders.security.JwtAuthenticationFilter;
import com.gustavosass.orders.service.AuthenticationService;
import com.gustavosass.orders.security.JwtService;
import com.gustavosass.orders.repository.UserRepository;

@WebMvcTest(AuthenticationController.class)
@Import({SecurityConfiguration.class, JwtAuthenticationFilter.class, ApplicationConfig.class})
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtService jwtService;

    private AuthenticationRequestDTO authRequest;
    private AuthenticationResponseDTO authResponse;

    @BeforeEach
    void setUp() {
        authRequest = AuthenticationRequestDTO.builder()
                .email("test@test.com")
                .password("password")
                .build();

        authResponse = AuthenticationResponseDTO.builder()
                .accessToken("access-token")
                .refreshToken("refresh-token")
                .expiresIn(3600L)
                .build();

        //when(jwtService.generateToken(any())).thenReturn("mocked-jwt-token");
        //when(jwtService.generateRefreshToken(any())).thenReturn("mocked-refresh-token");
        //when(jwtService.extractUsername(any())).thenReturn("test@test.com");
    }

    @Test
    @DisplayName("Deve autenticar usuário com sucesso")
    void whenAuthenticateThenReturnTokens() throws Exception {
        when(authenticationService.authenticate(any(AuthenticationRequestDTO.class)))
                .thenReturn(authResponse);

        mockMvc.perform(post("/api/v1/auth/authenticate")
                .servletPath("/api/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access-token"))
                .andExpect(jsonPath("$.refreshToken").value("refresh-token"))
                .andExpect(jsonPath("$.expiresIn").value(3600));
    }

    @Test
    @DisplayName("Deve atualizar token com sucesso")
    void whenRefreshTokenThenReturnNewTokens() throws Exception {
        doNothing().when(authenticationService).refreshToken(any(), any());

        mockMvc.perform(post("/api/v1/auth/refresh-token")
                .servletPath("/api/v1/auth/refresh-token")
                .header("Authorization", "Bearer refresh-token"))
                .andExpect(status().isOk());
    }

}