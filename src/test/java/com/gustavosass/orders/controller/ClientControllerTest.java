package com.gustavosass.orders.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Date;

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
import com.gustavosass.orders.dto.ClientDTO;
import com.gustavosass.orders.mapper.ClientMapper;
import com.gustavosass.orders.model.City;
import com.gustavosass.orders.model.Client;
import com.gustavosass.orders.model.Country;
import com.gustavosass.orders.model.State;
import com.gustavosass.orders.security.JwtAuthenticationFilter;
import com.gustavosass.orders.security.JwtService;
import com.gustavosass.orders.repository.UserRepository;
import com.gustavosass.orders.security.SecurityConfiguration;
import com.gustavosass.orders.service.ClientService;

@WebMvcTest(ClientController.class)
@Import({SecurityConfiguration.class, JwtAuthenticationFilter.class, ApplicationConfig.class})
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;

    @MockBean
    private ClientMapper clientMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserRepository userRepository;

    private Client client;
    private ClientDTO clientDTO;
    private City city;
    private State state;
    private Country country;


    @BeforeEach
    void setUp() {
        country = Country.builder()
                .id(1L)
                .name("Test Country")
                .build();
        
        state = State.builder()
                .id(1L)
                .name("Test State")
                .initials("TS")
                .country(country)
                .build();

        city = City.builder()
                .id(1L)
                .name("Test City")
                .state(state)
                .build();

        client = Client.builder()
                .id(1L)
                .name("Test Client")
                .email("test@test.com")
                .birthDate(new Date())
                .phone("123456789")
                .document("12345678900")
                .city(city)
                .street("Test Street")
                .number("123")
                .district("Test District")
                .complement("Test Complement")
                .postalCode("12345678")
                .build();

        clientDTO = ClientDTO.builder()
                .id(1L)
                .name("Test Client")
                .email("test@test.com")
                .birthDate(new Date())
                .phone("123456789")
                .document("12345678900")
                .city(city)
                .street("Test Street")
                .number("123")
                .district("Test District")
                .complement("Test Complement")
                .postalCode("12345678")
                .build();

        when(jwtService.generateToken(any())).thenReturn("mocked-jwt-token");
        when(jwtService.generateRefreshToken(any())).thenReturn("mocked-refresh-token");
        when(jwtService.isTokenValid(any(), any())).thenReturn(true);
        when(jwtService.extractUsername(any())).thenReturn("test@test.com");
    }

    @Test
    @WithMockUser
    @DisplayName("Get client by ID successfully")
    void whenGetClientByIdThenReturnClient() throws Exception {
        when(clientService.findById(1L)).thenReturn(client);
        when(clientMapper.toDTO(client)).thenReturn(clientDTO);

        mockMvc.perform(get("/api/v1/clients/1")
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(client.getId()))
                .andExpect(jsonPath("$.name").value(client.getName()))
                .andExpect(jsonPath("$.email").value(client.getEmail()));
    }

    @Test
    @WithMockUser
    @DisplayName("Get all clients successfully")
    void whenGetAllClientsThenReturnClientList() throws Exception {
        when(clientService.findAll()).thenReturn(Arrays.asList(client));
        when(clientMapper.toDTO(client)).thenReturn(clientDTO);

        mockMvc.perform(get("/api/v1/clients")
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(client.getId()))
                .andExpect(jsonPath("$[0].name").value(client.getName()))
                .andExpect(jsonPath("$[0].email").value(client.getEmail()));
    }

    @Test
    @WithMockUser
    @DisplayName("Create client successfully")
    void whenCreateClientThenReturnCreatedClient() throws Exception {
        when(clientMapper.toEntity(any(ClientDTO.class))).thenReturn(client);
        when(clientService.create(any(Client.class))).thenReturn(client);
        when(clientMapper.toDTO(client)).thenReturn(clientDTO);

        mockMvc.perform(post("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO))
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(client.getId()))
                .andExpect(jsonPath("$.name").value(client.getName()))
                .andExpect(jsonPath("$.email").value(client.getEmail()));
    }

    @Test
    @WithMockUser
    @DisplayName("Update client successfully")
    void whenUpdateClientThenReturnUpdatedClient() throws Exception {
        when(clientMapper.toEntity(any(ClientDTO.class))).thenReturn(client);
        when(clientService.update(any(Long.class), any(Client.class))).thenReturn(client);
        when(clientMapper.toDTO(client)).thenReturn(clientDTO);

        mockMvc.perform(put("/api/v1/clients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO))
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(client.getId()))
                .andExpect(jsonPath("$.name").value(client.getName()))
                .andExpect(jsonPath("$.email").value(client.getEmail()));
    }

    @Test
    @WithMockUser
    @DisplayName("Delete client successfully")
    void whenDeleteClientThenSuccess() throws Exception {
        doNothing().when(clientService).delete(1L);

        mockMvc.perform(delete("/api/v1/clients/1")
                .header("Authorization","Bearer mocked-jwt-token"))
                .andExpect(status().isNoContent());
    }
}