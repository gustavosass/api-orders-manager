package com.gustavosass.orders.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gustavosass.orders.dto.ClientDTO;
import com.gustavosass.orders.model.City;
import com.gustavosass.orders.model.Client;
import com.gustavosass.orders.model.State;
import com.gustavosass.orders.model.Country;

@ExtendWith(MockitoExtension.class)
class ClientMapperTest {

    private ClientMapper clientMapper;
    private Client client;
    private ClientDTO clientDTO;
    private City city;
    private State state;

    @BeforeEach
    void setUp() {
        clientMapper = new ClientMapper();

        state = State.builder()
                .id(1L)
                .name("Test State")
                .initials("TS")
                .country(Country.builder().id(1L).name("Test Country").build())
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
    }

    @Test
    @DisplayName("Convert ClientDTO to Client")
    void whenToEntityThenReturnClient() {
        Client mappedClient = clientMapper.toEntity(clientDTO);
        
        assertThat(mappedClient).isNotNull();
        assertThat(mappedClient.getId()).isEqualTo(clientDTO.getId());
        assertThat(mappedClient.getName()).isEqualTo(clientDTO.getName());
        assertThat(mappedClient.getEmail()).isEqualTo(clientDTO.getEmail());
        assertThat(mappedClient.getBirthDate()).isEqualTo(clientDTO.getBirthDate());
        assertThat(mappedClient.getPhone()).isEqualTo(clientDTO.getPhone());
        assertThat(mappedClient.getDocument()).isEqualTo(clientDTO.getDocument());
        assertThat(mappedClient.getCity()).isEqualTo(clientDTO.getCity());
        assertThat(mappedClient.getStreet()).isEqualTo(clientDTO.getStreet());
        assertThat(mappedClient.getNumber()).isEqualTo(clientDTO.getNumber());
        assertThat(mappedClient.getDistrict()).isEqualTo(clientDTO.getDistrict());
        assertThat(mappedClient.getComplement()).isEqualTo(clientDTO.getComplement());
        assertThat(mappedClient.getPostalCode()).isEqualTo(clientDTO.getPostalCode());
    }

    @Test
    @DisplayName("Convert Client to ClientDTO")
    void whenToDTOThenReturnClientDTO() {
        ClientDTO mappedDTO = clientMapper.toDTO(client);
        
        assertThat(mappedDTO).isNotNull();
        assertThat(mappedDTO.getId()).isEqualTo(client.getId());
        assertThat(mappedDTO.getName()).isEqualTo(client.getName());
        assertThat(mappedDTO.getEmail()).isEqualTo(client.getEmail());
        assertThat(mappedDTO.getBirthDate()).isEqualTo(client.getBirthDate());
        assertThat(mappedDTO.getPhone()).isEqualTo(client.getPhone());
        assertThat(mappedDTO.getDocument()).isEqualTo(client.getDocument());
        assertThat(mappedDTO.getCity()).isEqualTo(client.getCity());
        assertThat(mappedDTO.getStreet()).isEqualTo(client.getStreet());
        assertThat(mappedDTO.getNumber()).isEqualTo(client.getNumber());
        assertThat(mappedDTO.getDistrict()).isEqualTo(client.getDistrict());
        assertThat(mappedDTO.getComplement()).isEqualTo(client.getComplement());
        assertThat(mappedDTO.getPostalCode()).isEqualTo(client.getPostalCode());
    }
}