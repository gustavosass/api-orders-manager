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
import com.gustavosass.orders.model.country.dto.CountryDTO;
import com.gustavosass.orders.security.JwtAuthenticationFilter;
import com.gustavosass.orders.security.JwtService;
import com.gustavosass.orders.repository.UserRepository;
import com.gustavosass.orders.security.SecurityConfiguration;
import com.gustavosass.orders.service.CountryService;

@WebMvcTest(CountryController.class)
@Import({SecurityConfiguration.class, JwtAuthenticationFilter.class, ApplicationConfig.class})
class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CountryService countryService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserRepository userRepository;

    private CountryDTO countryDTO;

    @BeforeEach
    void setUp() {
        countryDTO = CountryDTO.builder()
                .id(1L)
                .name("Test Country")
                .build();

        when(jwtService.generateToken(any())).thenReturn("mocked-jwt-token");
        when(jwtService.generateRefreshToken(any())).thenReturn("mocked-refresh-token");
        when(jwtService.isTokenValid(any(), any())).thenReturn(true);
        when(jwtService.extractUsername(any())).thenReturn("test@test.com");
    }

    @Test
    @WithMockUser
    @DisplayName("Get country by ID successfully")
    void whenGetCountryByIdThenReturnCountry() throws Exception {
        when(countryService.findById(1L)).thenReturn(countryDTO);

        mockMvc.perform(get("/api/v1/country/1")
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(countryDTO.getId()))
                .andExpect(jsonPath("$.name").value(countryDTO.getName()));
    }

    @Test
    @WithMockUser
    @DisplayName("Get country by ID not found")
    void whenGetCountryByIdNotFoundThenReturn404() throws Exception {
        when(countryService.findById(999L)).thenThrow(new NoSuchElementException("Country not found"));

        mockMvc.perform(get("/api/v1/country/999")
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @DisplayName("Get all countries successfully")
    void whenGetAllCountriesThenReturnCountryList() throws Exception {
        when(countryService.findAll()).thenReturn(Arrays.asList(countryDTO));

        mockMvc.perform(get("/api/v1/country")
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(countryDTO.getId()))
                .andExpect(jsonPath("$[0].name").value(countryDTO.getName()));
    }

    @Test
    @WithMockUser
    @DisplayName("Create country successfully")
    void whenCreateCountryThenReturnCreatedCountry() throws Exception {
        when(countryService.create(any(CountryDTO.class))).thenReturn(countryDTO);

        mockMvc.perform(post("/api/v1/country")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(countryDTO))
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(countryDTO.getId()))
                .andExpect(jsonPath("$.name").value(countryDTO.getName()));
    }

    @Test
    @WithMockUser
    @DisplayName("Update country successfully")
    void whenUpdateCountryThenReturnUpdatedCountry() throws Exception {
        when(countryService.update(any(Long.class), any(CountryDTO.class))).thenReturn(countryDTO);

        mockMvc.perform(put("/api/v1/country/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(countryDTO))
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(countryDTO.getId()))
                .andExpect(jsonPath("$.name").value(countryDTO.getName()));
    }

    @Test
    @WithMockUser
    @DisplayName("Update country not found")
    void whenUpdateCountryNotFoundThenReturn404() throws Exception {
        when(countryService.update(any(Long.class), any(CountryDTO.class)))
            .thenThrow(new NoSuchElementException("Country not found"));

        mockMvc.perform(put("/api/v1/country/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(countryDTO))
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @DisplayName("Delete country successfully")
    void whenDeleteCountryThenSuccess() throws Exception {
        doNothing().when(countryService).delete(1L);

        mockMvc.perform(delete("/api/v1/country/1")
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    @DisplayName("Delete country not found")
    void whenDeleteCountryNotFoundThenReturn404() throws Exception {
        doThrow(new NoSuchElementException("Country not found")).when(countryService).delete(999L);

        mockMvc.perform(delete("/api/v1/country/999")
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isNotFound());
    }
} 