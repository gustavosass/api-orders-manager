package com.gustavosass.orders.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gustavosass.orders.dto.AddressDTO;
import com.gustavosass.orders.dto.ClientDTO;
import com.gustavosass.orders.model.Address;
import com.gustavosass.orders.model.City;
import com.gustavosass.orders.model.Client;
import com.gustavosass.orders.model.State;
import com.gustavosass.orders.model.Country;

@SpringBootTest
class ClientMapperTest {

    @Autowired
    private ClientMapper clientMapper;

    private Client client;
    private ClientDTO clientDTO;
    private AddressDTO addressDTO;
    private Address address;
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

        address = Address.builder()
                .id(1L)
                .city(city)
                .street("Test Street")
                .number("123")
                .district("Test District")
                .complement("Test Complement")
                .postalCode("12345678")
                .build();

        addressDTO = AddressDTO.builder()
                .city(city)
                .street("Test Street")
                .number("123")
                .district("Test District")
                .complement("Test Complement")
                .postalCode("12345678")
                .build();

        client = Client.builder()
                .id(1L)
                .name("Test Client")
                .email("test@test.com")
                .birthDate(new Date())
                .phone("123456789")
                .document("12345678900")
                .address(address)
                .build();

        clientDTO = ClientDTO.builder()
                .id(1L)
                .name("Test Client")
                .email("test@test.com")
                .birthDate(client.getBirthDate())
                .phone("123456789")
                .document("12345678900")
                .addressDTO(addressDTO)
                .build();
    }

    @Test
    @DisplayName("Converter ClientDTO para Client")
    void whenToEntityThenReturnClient() {
                
        Client client = clientMapper.toEntity(clientDTO);
        
        assertThat(client).isNotNull();
        assertThat(client.getId()).isEqualTo(clientDTO.getId());
        assertThat(client.getName()).isEqualTo(clientDTO.getName());
        assertThat(client.getEmail()).isEqualTo(clientDTO.getEmail());
        assertThat(client.getBirthDate()).isEqualTo(clientDTO.getBirthDate());
        assertThat(client.getPhone()).isEqualTo(clientDTO.getPhone());
        assertThat(client.getDocument()).isEqualTo(clientDTO.getDocument());
        assertThat(client.getAddress().getId()).isEqualTo(clientDTO.getAddressDTO().getId());
        assertThat(client.getAddress().getStreet()).isEqualTo(clientDTO.getAddressDTO().getStreet());
        assertThat(client.getAddress().getNumber()).isEqualTo(clientDTO.getAddressDTO().getNumber());
        assertThat(client.getAddress().getDistrict()).isEqualTo(clientDTO.getAddressDTO().getDistrict());
        assertThat(client.getAddress().getComplement()).isEqualTo(clientDTO.getAddressDTO().getComplement());
        assertThat(client.getAddress().getPostalCode()).isEqualTo(clientDTO.getAddressDTO().getPostalCode());
    }

    @Test
    @DisplayName("Converter Client para ClientDTO")
    void whenToDTOThenReturnClientDTO() {
        
        ClientDTO mappedDTO = clientMapper.toDTO(client);
        
        assertThat(mappedDTO).isNotNull();
        assertThat(mappedDTO.getId()).isEqualTo(client.getId());
        assertThat(mappedDTO.getName()).isEqualTo(client.getName());
        assertThat(mappedDTO.getEmail()).isEqualTo(client.getEmail());
        assertThat(mappedDTO.getBirthDate()).isEqualTo(client.getBirthDate());
        assertThat(mappedDTO.getPhone()).isEqualTo(client.getPhone());
        assertThat(mappedDTO.getDocument()).isEqualTo(client.getDocument());
        assertThat(mappedDTO.getAddressDTO().getId()).isEqualTo(addressDTO.getId());
        assertThat(mappedDTO.getAddressDTO().getStreet()).isEqualTo(addressDTO.getStreet());
        assertThat(mappedDTO.getAddressDTO().getNumber()).isEqualTo(addressDTO.getNumber());
        assertThat(mappedDTO.getAddressDTO().getDistrict()).isEqualTo(addressDTO.getDistrict());
        assertThat(mappedDTO.getAddressDTO().getComplement()).isEqualTo(addressDTO.getComplement());
        assertThat(mappedDTO.getAddressDTO().getPostalCode()).isEqualTo(addressDTO.getPostalCode());
    }
}