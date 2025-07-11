package com.gustavosass.orders.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.boot.test.context.SpringBootTest;

import com.gustavosass.orders.mapper.AddressMapper;
import com.gustavosass.orders.mapper.ClientMapper;
import com.gustavosass.orders.model.Address;
import com.gustavosass.orders.dto.AddressCreateDTO;
import com.gustavosass.orders.dto.AddressDTO;
import com.gustavosass.orders.dto.AddressUpdateDTO;
import com.gustavosass.orders.model.City;
import com.gustavosass.orders.dto.CityDTO;
import com.gustavosass.orders.model.Client;
import com.gustavosass.orders.dto.ClientCreateDTO;
import com.gustavosass.orders.dto.ClientDTO;
import com.gustavosass.orders.dto.ClientUpdateDTO;
import com.gustavosass.orders.model.Country;
import com.gustavosass.orders.dto.CountryDTO;
import com.gustavosass.orders.model.State;
import com.gustavosass.orders.dto.StateDTO;
import com.gustavosass.orders.repository.ClientRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;
    
    @Mock
    private AddressService addressService;
    
    @Mock
    private AddressMapper addressMapper;
    
    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientService clientService;

    private Client client;
    private ClientDTO clientDTO;
    private ClientCreateDTO clientCreateDTO;
    private ClientUpdateDTO clientUpdateDTO;
    private Address address;
    private AddressDTO addressDTO;
    private AddressCreateDTO addressCreateDTO;
    private AddressUpdateDTO addressUpdateDTO;
    private City city;
    private CityDTO cityDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Country country = Country.builder()
                .id(1L)
                .name("Test Country")
                .build();
                
        CountryDTO countryDTO = CountryDTO.builder()
                .id(1L)
                .name("Test Country")
                .build();

        State state = State.builder()
                .id(1L)
                .name("Test State")
                .initials("TS")
                .country(country)
                .build();
                
        StateDTO stateDTO = StateDTO.builder()
                .id(1L)
                .name("Test State")
                .initials("TS")
                .countryDTO(countryDTO)
                .build();

        city = City.builder()
                .id(1L)
                .name("Test City")
                .state(state)
                .build();
                
        cityDTO = CityDTO.builder()
                .id(1L)
                .name("Test City")
                .stateDTO(stateDTO)
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
                .id(1L)
                .cityDTO(cityDTO)
                .street("Test Street")
                .number("123")
                .district("Test District")
                .complement("Test Complement")
                .postalCode("12345678")
                .build();
                
        addressCreateDTO = AddressCreateDTO.builder()
                .cityId(1L)
                .countryId(1L)
                .stateId(1L)
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
                .birthDate(new Date())
                .phone("123456789")
                .document("12345678900")
                .addressDTO(addressDTO)
                .build();
                
        clientCreateDTO = ClientCreateDTO.builder()
                .name("Test Client")
                .email("test@test.com")
                .birthDate(new Date())
                .phone("123456789")
                .document("12345678900")
                .addressCreateDTO(addressCreateDTO)
                .build();
                
        clientUpdateDTO = ClientUpdateDTO.builder()
                .id(1L)
                .name("Test Client")
                .email("test@test.com")
                .birthDate(new Date())
                .phone("123456789")
                .document("12345678900")
                .addressUpdateDTO(addressUpdateDTO)
                .build();
    }

    @Test
    @DisplayName("Find client by ID successfully")
    void whenFindByIdThenReturnClient() {
        when(clientRepository.findById(any(Long.class))).thenReturn(Optional.of(client));
        when(clientMapper.toDTO(any(Client.class))).thenReturn(clientDTO);

        ClientDTO found = clientService.findById(1L);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(client.getId());
    }

    @Test
    @DisplayName("Throw exception when client ID not found")
    void whenFindByIdNotFoundThenThrowException() {
        when(clientRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class,
                () -> clientService.findById(1L));

        assertThat(exception.getMessage()).isEqualTo("Client not found");
    }

    @Test
    @DisplayName("Find client by email successfully")
    void whenFindByEmailThenReturnClient() {
        when(clientRepository.findByEmail(any(String.class))).thenReturn(Optional.of(client));
        when(clientMapper.toDTO(any(Client.class))).thenReturn(clientDTO);

        ClientDTO found = clientService.findByEmail("test@test.com");

        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo(client.getEmail());
    }

    @Test
    @DisplayName("Find client by document successfully")
    void whenFindByDocumentThenReturnClient() {
        when(clientRepository.findByDocument(any(String.class))).thenReturn(Optional.of(client));
        when(clientMapper.toDTO(any(Client.class))).thenReturn(clientDTO);

        ClientDTO found = clientService.findByDocument("12345678900");

        assertThat(found).isNotNull();
        assertThat(found.getDocument()).isEqualTo(client.getDocument());
    }

    @Test
    @DisplayName("Find all clients successfully")
    void whenFindAllThenReturnClientList() {
        when(clientRepository.findAll()).thenReturn(Arrays.asList(client));
        when(clientMapper.toDTO(any(Client.class))).thenReturn(clientDTO);

        List<ClientDTO> clients = clientService.findAll();

        assertThat(clients).isNotEmpty();
        assertThat(clients).hasSize(1);
        assertThat(clients.get(0).getId()).isEqualTo(client.getId());
    }

    @Test
    @DisplayName("Create client successfully")
    void whenCreateClientThenSuccess() {
        when(clientRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        when(clientRepository.findByDocument(any(String.class))).thenReturn(Optional.empty());
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(clientMapper.toEntity(any(ClientCreateDTO.class))).thenReturn(client);
        when(clientMapper.toDTO(any(Client.class))).thenReturn(clientDTO);
        when(addressService.create(any(AddressCreateDTO.class))).thenReturn(addressDTO);
        when(addressMapper.toEntity(any(AddressDTO.class))).thenReturn(address);

        ClientDTO created = clientService.create(clientCreateDTO);

        assertThat(created).isNotNull();
        assertThat(created.getId()).isEqualTo(client.getId());
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    @DisplayName("Update client successfully")
    void whenUpdateClientThenSuccess() {
        Client updatedClient = client;
        updatedClient.setName("Updated Name");
        
        ClientDTO updatedClientDTO = clientDTO;
        updatedClientDTO.setName("Updated Name");

        when(clientRepository.findById(any(Long.class))).thenReturn(Optional.of(client));
        when(clientMapper.toEntity(any(ClientUpdateDTO.class))).thenReturn(client);
        when(clientMapper.toDTO(any(Client.class))).thenReturn(updatedClientDTO);
        when(clientRepository.save(any(Client.class))).thenReturn(updatedClient);
        when(addressService.update(any(Long.class), any(AddressUpdateDTO.class))).thenReturn(addressDTO);
        when(addressMapper.toEntity(any(AddressDTO.class))).thenReturn(address);

        ClientDTO result = clientService.update(1L, clientUpdateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Name");
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    @DisplayName("Delete client successfully")
    void whenDeleteClientThenSuccess() {
        when(clientRepository.findById(any(Long.class))).thenReturn(Optional.of(client));
        when(clientMapper.toDTO(any(Client.class))).thenReturn(clientDTO);

        clientService.delete(1L);

        verify(clientRepository).deleteById(any(Long.class));
    }
}