package com.gustavosass.orders.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.gustavosass.orders.model.address.Address;
import com.gustavosass.orders.model.city.City;
import com.gustavosass.orders.model.client.Client;
import com.gustavosass.orders.model.country.Country;
import com.gustavosass.orders.model.state.State;

@DataJpaTest
@ActiveProfiles("test")
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private CountryRepository countryRepository;
    
    @Autowired
    private StateRepository stateRepository;
    
    @Autowired
    private CityRepository cityRepository;

    private Client client;
    private Address address;

    @BeforeEach
    void setUp() {
        Country country = countryRepository.save(Country.builder()
                .name("Test Country")
                .build());

        State state = stateRepository.save(State.builder()
                .name("Test State")
                .initials("TS")
                .country(country)
                .build());

        City city = cityRepository.save(City.builder()
                .name("Test City")
                .state(state)
                .build());

        address = addressRepository.save(Address.builder()
                .city(city)
                .street("Test Street")
                .number("123")
                .district("Test District")
                .complement("Test Complement")
                .postalCode("12345678")
                .build());

        client = Client.builder()
                .name("Test Client")
                .email("test@test.com")
                .birthDate(new Date())
                .phone("123456789")
                .document("12345678900")
                .address(address)
                .build();
    }

    @Test
    @DisplayName("Save client successfully")
    void whenSaveClientThenReturnSavedClient() {
        Client savedClient = clientRepository.save(client);
        
        assertThat(savedClient).isNotNull();
        assertThat(savedClient.getId()).isNotNull();
        assertThat(savedClient.getName()).isEqualTo(client.getName());
        assertThat(savedClient.getEmail()).isEqualTo(client.getEmail());
        assertThat(savedClient.getDocument()).isEqualTo(client.getDocument());
    }

    @Test
    @DisplayName("Find client by email")
    void whenFindByEmailThenReturnClient() {
        clientRepository.save(client);
        
        var foundClient = clientRepository.findByEmail(client.getEmail());
        
        assertThat(foundClient).isPresent();
        assertThat(foundClient.get().getEmail()).isEqualTo(client.getEmail());
    }

    @Test
    @DisplayName("Find client by document")
    void whenFindByDocumentThenReturnClient() {
        clientRepository.save(client);
        
        var foundClient = clientRepository.findByDocument(client.getDocument());
        
        assertThat(foundClient).isPresent();
        assertThat(foundClient.get().getDocument()).isEqualTo(client.getDocument());
    }

    @Test
    @DisplayName("Return empty when email not found")
    void whenFindByNonExistingEmailThenReturnEmpty() {
        var foundClient = clientRepository.findByEmail("nonexisting@test.com");
        
        assertThat(foundClient).isEmpty();
    }

    @Test
    @DisplayName("Return empty when document not found")
    void whenFindByNonExistingDocumentThenReturnEmpty() {
        var foundClient = clientRepository.findByDocument("99999999999");
        
        assertThat(foundClient).isEmpty();
    }
}