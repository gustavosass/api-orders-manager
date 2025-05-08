package com.gustavosass.orders.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.gustavosass.orders.mapper.ClientMapper;
import com.gustavosass.orders.model.City;
import com.gustavosass.orders.model.Client;
import com.gustavosass.orders.model.Country;
import com.gustavosass.orders.model.State;
import com.gustavosass.orders.repository.ClientRepository;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientMapper clientMapper;
    @InjectMocks
    private ClientService clientService;

    @Autowired
    private Client client;

    @Autowired
    private City city;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Country country = Country.builder()
                .id(1L)
                .name("Test Country")
                .build();

        State state = State.builder()
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
    }

    @Test
    @DisplayName("Find client by ID successfully")
    void whenFindByIdThenReturnClient() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Client found = clientService.findById(1L);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(client.getId());
    }

    @Test
    @DisplayName("Throw exception when client ID not found")
    void whenFindByIdNotFoundThenThrowException() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class,
                () -> clientService.findById(1L));

        assertThat(exception.getMessage()).isEqualTo("Client not found");
    }

    @Test
    @DisplayName("Find client by email successfully")
    void whenFindByEmailThenReturnClient() {
        when(clientRepository.findByEmail(client.getEmail()))
                .thenReturn(Optional.of(client));

        Client found = clientService.findByEmail(client.getEmail());

        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo(client.getEmail());
    }

    @Test
    @DisplayName("Find client by document successfully")
    void whenFindByDocumentThenReturnClient() {
        when(clientRepository.findByDocument(client.getDocument()))
                .thenReturn(Optional.of(client));

        Client found = clientService.findByDocument(client.getDocument());

        assertThat(found).isNotNull();
        assertThat(found.getDocument()).isEqualTo(client.getDocument());
    }

    @Test
    @DisplayName("Find all clients successfully")
    void whenFindAllThenReturnClientList() {
        when(clientRepository.findAll()).thenReturn(Arrays.asList(client));

        List<Client> clients = clientService.findAll();

        assertThat(clients).isNotEmpty();
        assertThat(clients).hasSize(1);
        assertThat(clients.get(0).getId()).isEqualTo(client.getId());
    }

    @Test
    @DisplayName("Create client successfully")
    void whenCreateClientThenSuccess() {
        when(clientRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(clientRepository.findByDocument(any())).thenReturn(Optional.empty());
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client created = clientService.create(client);

        assertThat(created).isNotNull();
        assertThat(created.getId()).isEqualTo(client.getId());
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    @DisplayName("Throw exception when creating client with existing email")
    void whenCreateClientWithExistingEmailThenThrowException() {
        when(clientRepository.findByEmail(client.getEmail()))
                .thenReturn(Optional.of(client));

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> clientService.create(client));

        assertThat(exception.getMessage()).isEqualTo("Email already registered");
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    @DisplayName("Update client successfully")
    void whenUpdateClientThenSuccess() {
        Client updatedClient = client;
        updatedClient.setName("Updated Name");


        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
  
        when(clientRepository.save(any(Client.class))).thenReturn(updatedClient);

        Client result = clientService.update(1L, updatedClient);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Name");
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    @DisplayName("Delete client successfully")
    void whenDeleteClientThenSuccess() {
        when(clientRepository.existsById(1L)).thenReturn(true);

        clientService.delete(1L);

        verify(clientRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Throw exception when deleting non-existent client")
    void whenDeleteNonExistentClientThenThrowException() {
        when(clientRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(NoSuchElementException.class,
                () -> clientService.delete(1L));

        assertThat(exception.getMessage()).isEqualTo("Client not found");
        verify(clientRepository, never()).deleteById(any());
    }
}