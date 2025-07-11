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
import com.gustavosass.orders.dto.CityDTO;
import com.gustavosass.orders.dto.CountryDTO;
import com.gustavosass.orders.dto.StateDTO;
import com.gustavosass.orders.security.JwtAuthenticationFilter;
import com.gustavosass.orders.security.JwtService;
import com.gustavosass.orders.repository.UserRepository;
import com.gustavosass.orders.security.SecurityConfiguration;
import com.gustavosass.orders.service.CityService;

@WebMvcTest(CityController.class)
@Import({SecurityConfiguration.class, JwtAuthenticationFilter.class, ApplicationConfig.class})
class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CityService cityService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserRepository userRepository;

    private CityDTO cityDTO;

    @BeforeEach
    void setUp() {
        CountryDTO countryDTO = CountryDTO.builder()
                .id(1L)
                .name("Test Country")
                .build();
        
        StateDTO stateDTO = StateDTO.builder()
                .id(1L)
                .name("Test State")
                .initials("TS")
                .countryDTO(countryDTO)
                .build();

        cityDTO = CityDTO.builder()
                .id(1L)
                .name("Test City")
                .stateDTO(stateDTO)
                .build();

        when(jwtService.generateToken(any())).thenReturn("mocked-jwt-token");
        when(jwtService.generateRefreshToken(any())).thenReturn("mocked-refresh-token");
        when(jwtService.isTokenValid(any(), any())).thenReturn(true);
        when(jwtService.extractUsername(any())).thenReturn("test@test.com");
    }

    @Test
    @WithMockUser
    @DisplayName("Get city by ID successfully")
    void whenGetCityByIdThenReturnCity() throws Exception {
        when(cityService.findById(1L)).thenReturn(cityDTO);

        mockMvc.perform(get("/api/v1/city/1")
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(cityDTO.getId()))
                .andExpect(jsonPath("$.name").value(cityDTO.getName()));
    }

    @Test
    @WithMockUser
    @DisplayName("Get city by ID not found")
    void whenGetCityByIdNotFoundThenReturn404() throws Exception {
        when(cityService.findById(999L)).thenThrow(new NoSuchElementException("City not found"));

        mockMvc.perform(get("/api/v1/city/999")
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @DisplayName("Get all cities successfully")
    void whenGetAllCitiesThenReturnCityList() throws Exception {
        when(cityService.findAll()).thenReturn(Arrays.asList(cityDTO));

        mockMvc.perform(get("/api/v1/city")
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(cityDTO.getId()))
                .andExpect(jsonPath("$[0].name").value(cityDTO.getName()));
    }

    @Test
    @WithMockUser
    @DisplayName("Create city successfully")
    void whenCreateCityThenReturnCreatedCity() throws Exception {
        when(cityService.create(any(CityDTO.class))).thenReturn(cityDTO);

        mockMvc.perform(post("/api/v1/city")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cityDTO))
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(cityDTO.getId()))
                .andExpect(jsonPath("$.name").value(cityDTO.getName()));
    }

    @Test
    @WithMockUser
    @DisplayName("Update city successfully")
    void whenUpdateCityThenReturnUpdatedCity() throws Exception {
        when(cityService.update(any(Long.class), any(CityDTO.class))).thenReturn(cityDTO);

        mockMvc.perform(put("/api/v1/city/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cityDTO))
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(cityDTO.getId()))
                .andExpect(jsonPath("$.name").value(cityDTO.getName()));
    }

    @Test
    @WithMockUser
    @DisplayName("Update city not found")
    void whenUpdateCityNotFoundThenReturn404() throws Exception {
        when(cityService.update(any(Long.class), any(CityDTO.class)))
            .thenThrow(new NoSuchElementException("City not found"));

        mockMvc.perform(put("/api/v1/city/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cityDTO))
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @DisplayName("Delete city successfully")
    void whenDeleteCityThenSuccess() throws Exception {
        doNothing().when(cityService).delete(1L);

        mockMvc.perform(delete("/api/v1/city/1")
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    @DisplayName("Delete city not found")
    void whenDeleteCityNotFoundThenReturn404() throws Exception {
        doThrow(new NoSuchElementException("City not found")).when(cityService).delete(999L);

        mockMvc.perform(delete("/api/v1/city/999")
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isNotFound());
    }
} 