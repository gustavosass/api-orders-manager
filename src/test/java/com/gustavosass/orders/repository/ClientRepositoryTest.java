package com.gustavosass.orders.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.gustavosass.orders.model.City;
import com.gustavosass.orders.model.Client;
import com.gustavosass.orders.model.Country;
import com.gustavosass.orders.model.State;

@DataJpaTest
@ActiveProfiles("test")
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private CountryRepository countryRepository;
    
    @Autowired
    private StateRepository stateRepository;
    
    @Autowired
    private CityRepository cityRepository;

    private Client client;
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

        client = Client.builder()
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