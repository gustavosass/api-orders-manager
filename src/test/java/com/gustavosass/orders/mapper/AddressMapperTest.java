package com.gustavosass.orders.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gustavosass.orders.model.address.Address;
import com.gustavosass.orders.model.address.dto.AddressDTO;
import com.gustavosass.orders.model.city.City;
import com.gustavosass.orders.model.country.Country;
import com.gustavosass.orders.model.state.State;

@ExtendWith(MockitoExtension.class)
class AddressMapperTest {

    private AddressMapper addressMapper;
    private Address address;
    private AddressDTO addressDTO;
    private City city;
    private State state;
    private Country country;

    @BeforeEach
    void setUp() {
        addressMapper = new AddressMapper();

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
    }

    @Test
    @DisplayName("Convert AddressDTO to Address")
    void whenToEntityThenReturnAddress() {
        Address mappedAddress = addressMapper.toEntity(addressDTO);
        
        assertThat(mappedAddress).isNotNull();
        assertThat(mappedAddress.getCity()).isEqualTo(addressDTO.getCity());
        assertThat(mappedAddress.getStreet()).isEqualTo(addressDTO.getStreet());
        assertThat(mappedAddress.getNumber()).isEqualTo(addressDTO.getNumber());
        assertThat(mappedAddress.getDistrict()).isEqualTo(addressDTO.getDistrict());
        assertThat(mappedAddress.getComplement()).isEqualTo(addressDTO.getComplement());
        assertThat(mappedAddress.getPostalCode()).isEqualTo(addressDTO.getPostalCode());
    }

    @Test
    @DisplayName("Convert Address to AddressDTO")
    void whenToDTOThenReturnAddressDTO() {
        AddressDTO mappedDTO = addressMapper.toDTO(address);
        
        assertThat(mappedDTO).isNotNull();
        assertThat(mappedDTO.getCity()).isEqualTo(address.getCity());
        assertThat(mappedDTO.getStreet()).isEqualTo(address.getStreet());
        assertThat(mappedDTO.getNumber()).isEqualTo(address.getNumber());
        assertThat(mappedDTO.getDistrict()).isEqualTo(address.getDistrict());
        assertThat(mappedDTO.getComplement()).isEqualTo(address.getComplement());
        assertThat(mappedDTO.getPostalCode()).isEqualTo(address.getPostalCode());
    }
}