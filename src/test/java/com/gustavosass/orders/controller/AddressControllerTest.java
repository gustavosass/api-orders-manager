package com.gustavosass.orders.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import com.gustavosass.orders.dto.AddressDTO;
import com.gustavosass.orders.dto.AddressUpdateDTO;
import com.gustavosass.orders.dto.CityDTO;
import com.gustavosass.orders.dto.CountryDTO;
import com.gustavosass.orders.dto.StateDTO;
import com.gustavosass.orders.security.JwtAuthenticationFilter;
import com.gustavosass.orders.security.JwtService;
import com.gustavosass.orders.repository.UserRepository;
import com.gustavosass.orders.security.SecurityConfiguration;
import com.gustavosass.orders.service.AddressService;

@WebMvcTest(AddressController.class)
@Import({SecurityConfiguration.class, JwtAuthenticationFilter.class, ApplicationConfig.class})
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AddressService addressService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserRepository userRepository;

    private AddressDTO addressDTO;
    private AddressUpdateDTO addressUpdateDTO;

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

        CityDTO cityDTO = CityDTO.builder()
                .id(1L)
                .name("Test City")
                .stateDTO(stateDTO)
                .build();
        
        addressDTO = AddressDTO.builder()
                .id(1L)
                .cityDTO(cityDTO)
                .street("Test Street")
                .number("123")
                .district("Test District")
                .complement("Test Complement")
                .postalCode("12345678")
                .build();
                
        addressUpdateDTO = AddressUpdateDTO.builder()
                .id(1L)
                .cityId(1L)
                .countryId(1L)
                .stateId(1L)
                .street("Updated Street")
                .number("456")
                .district("Updated District")
                .complement("Updated Complement")
                .postalCode("87654321")
                .build();

        when(jwtService.generateToken(any())).thenReturn("mocked-jwt-token");
        when(jwtService.generateRefreshToken(any())).thenReturn("mocked-refresh-token");
        when(jwtService.isTokenValid(any(), any())).thenReturn(true);
        when(jwtService.extractUsername(any())).thenReturn("test@test.com");
    }

    @Test
    @WithMockUser
    @DisplayName("Get address by ID successfully")
    void whenGetAddressByIdThenReturnAddress() throws Exception {
        when(addressService.findById(1L)).thenReturn(addressDTO);

        mockMvc.perform(get("/api/v1/clients/address/1")
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(addressDTO.getId()))
                .andExpect(jsonPath("$.street").value(addressDTO.getStreet()))
                .andExpect(jsonPath("$.number").value(addressDTO.getNumber()));
    }

    @Test
    @WithMockUser
    @DisplayName("Get address by ID not found")
    void whenGetAddressByIdNotFoundThenReturn404() throws Exception {
        when(addressService.findById(999L)).thenThrow(new NoSuchElementException("Address not found"));

        mockMvc.perform(get("/api/v1/clients/address/999")
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @DisplayName("Update address successfully")
    void whenUpdateAddressThenReturnUpdatedAddress() throws Exception {
        when(addressService.update(any(Long.class), any(AddressUpdateDTO.class))).thenReturn(addressDTO);

        mockMvc.perform(put("/api/v1/clients/address/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addressUpdateDTO))
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(addressDTO.getId()))
                .andExpect(jsonPath("$.street").value(addressDTO.getStreet()))
                .andExpect(jsonPath("$.number").value(addressDTO.getNumber()));
    }

    @Test
    @WithMockUser
    @DisplayName("Update address not found")
    void whenUpdateAddressNotFoundThenReturn404() throws Exception {
        when(addressService.update(any(Long.class), any(AddressUpdateDTO.class)))
            .thenThrow(new NoSuchElementException("Address not found"));

        mockMvc.perform(put("/api/v1/clients/address/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addressUpdateDTO))
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isNotFound());
    }
} 