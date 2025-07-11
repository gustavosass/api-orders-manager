package com.gustavosass.orders.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.gustavosass.orders.model.Address;
import com.gustavosass.orders.model.City;
import com.gustavosass.orders.model.Country;
import com.gustavosass.orders.model.State;

@DataJpaTest
@ActiveProfiles("test")
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private CountryRepository countryRepository;
    
    @Autowired
    private StateRepository stateRepository;
    
    @Autowired
    private CityRepository cityRepository;

    private Address address;
    private City city;
    private State state;
    private Country country;

    @BeforeEach
    void setUp() {
        country = Country.builder()
                .name("Test Country")
                .build();
        country = countryRepository.save(country);

        state = State.builder()
                .name("Test State")
                .initials("TS")
                .country(country)
                .build();
        state = stateRepository.save(state);

        city = City.builder()
                .name("Test City")
                .state(state)
                .build();
        city = cityRepository.save(city);

        address = Address.builder()
                .city(city)
                .street("Test Street")
                .number("123")
                .district("Test District")
                .complement("Test Complement")
                .postalCode("12345678")
                .build();
    }

    @Test
    @DisplayName("Save address successfully")
    void whenSaveAddressThenReturnSavedAddress() {
        Address savedAddress = addressRepository.save(address);
        
        assertThat(savedAddress).isNotNull();
        assertThat(savedAddress.getId()).isNotNull();
        assertThat(savedAddress.getStreet()).isEqualTo(address.getStreet());
        assertThat(savedAddress.getNumber()).isEqualTo(address.getNumber());
        assertThat(savedAddress.getDistrict()).isEqualTo(address.getDistrict());
        assertThat(savedAddress.getComplement()).isEqualTo(address.getComplement());
        assertThat(savedAddress.getPostalCode()).isEqualTo(address.getPostalCode());
        assertThat(savedAddress.getCity()).isEqualTo(city);
    }
}