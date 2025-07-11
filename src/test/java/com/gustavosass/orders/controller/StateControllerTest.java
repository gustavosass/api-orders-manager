package com.gustavosass.orders.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavosass.orders.config.ApplicationConfig;
import com.gustavosass.orders.dto.CountryDTO;
import com.gustavosass.orders.dto.StateDTO;
import com.gustavosass.orders.security.JwtAuthenticationFilter;
import com.gustavosass.orders.security.JwtService;
import com.gustavosass.orders.repository.UserRepository;
import com.gustavosass.orders.security.SecurityConfiguration;
import com.gustavosass.orders.service.StateService;

@WebMvcTest(StateController.class)
@Import({SecurityConfiguration.class, JwtAuthenticationFilter.class, ApplicationConfig.class})
class StateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StateService stateService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserRepository userRepository;

    private StateDTO stateDTO;

    @BeforeEach
    void setUp() {
        CountryDTO countryDTO = CountryDTO.builder()
                .id(1L)
                .name("Test Country")
                .build();
        
        stateDTO = StateDTO.builder()
                .id(1L)
                .name("Test State")
                .initials("TS")
                .countryDTO(countryDTO)
                .build();

        when(jwtService.generateToken(any())).thenReturn("mocked-jwt-token");
        when(jwtService.generateRefreshToken(any())).thenReturn("mocked-refresh-token");
        when(jwtService.isTokenValid(any(), any())).thenReturn(true);
        when(jwtService.extractUsername(any())).thenReturn("test@test.com");
    }

    @Test
    @WithMockUser
    @DisplayName("Get state by ID successfully")
    void whenGetStateByIdThenReturnState() throws Exception {
        when(stateService.findById(1L)).thenReturn(stateDTO);

        mockMvc.perform(get("/v1/api/state/1")
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(stateDTO.getId()))
                .andExpect(jsonPath("$.name").value(stateDTO.getName()))
                .andExpect(jsonPath("$.initials").value(stateDTO.getInitials()));
    }

    @Test
    @WithMockUser
    @DisplayName("Get state by ID not found")
    void whenGetStateByIdNotFoundThenReturn404() throws Exception {
        when(stateService.findById(999L)).thenThrow(new NoSuchElementException("State not found"));

        mockMvc.perform(get("/v1/api/state/999")
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @DisplayName("Get all states successfully")
    void whenGetAllStatesThenReturnStateList() throws Exception {
        when(stateService.findAll()).thenReturn(Arrays.asList(stateDTO));

        mockMvc.perform(get("/v1/api/state")
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(stateDTO.getId()))
                .andExpect(jsonPath("$[0].name").value(stateDTO.getName()))
                .andExpect(jsonPath("$[0].initials").value(stateDTO.getInitials()));
    }

    @Test
    @WithMockUser
    @DisplayName("Create state successfully")
    void whenCreateStateThenReturnCreatedState() throws Exception {
        when(stateService.create(any(StateDTO.class))).thenReturn(stateDTO);

        mockMvc.perform(post("/v1/api/state")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stateDTO))
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(stateDTO.getId()))
                .andExpect(jsonPath("$.name").value(stateDTO.getName()))
                .andExpect(jsonPath("$.initials").value(stateDTO.getInitials()));
    }

    @Test
    @WithMockUser
    @DisplayName("Update state successfully")
    void whenUpdateStateThenReturnUpdatedState() throws Exception {
        when(stateService.update(any(Long.class), any(StateDTO.class))).thenReturn(stateDTO);

        mockMvc.perform(put("/v1/api/state/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stateDTO))
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(stateDTO.getId()))
                .andExpect(jsonPath("$.name").value(stateDTO.getName()))
                .andExpect(jsonPath("$.initials").value(stateDTO.getInitials()));
    }

    @Test
    @WithMockUser
    @DisplayName("Update state not found")
    void whenUpdateStateNotFoundThenReturn404() throws Exception {
        when(stateService.update(any(Long.class), any(StateDTO.class)))
            .thenThrow(new NoSuchElementException("State not found"));

        mockMvc.perform(put("/v1/api/state/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stateDTO))
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @DisplayName("Delete state successfully")
    void whenDeleteStateThenSuccess() throws Exception {
        doNothing().when(stateService).delete(1L);

        mockMvc.perform(delete("/v1/api/state/1")
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    @DisplayName("Delete state not found")
    void whenDeleteStateNotFoundThenReturn404() throws Exception {
        doThrow(new NoSuchElementException("State not found")).when(stateService).delete(999L);

        mockMvc.perform(delete("/v1/api/state/999")
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isNotFound());
    }
} 