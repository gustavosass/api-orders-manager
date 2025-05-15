package com.gustavosass.orders.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.gustavosass.orders.integration.viacep.ViaCepClient;
import com.gustavosass.orders.mapper.AddressMapper;
import com.gustavosass.orders.mapper.CityMapper;
import com.gustavosass.orders.mapper.CountryMapper;
import com.gustavosass.orders.mapper.StateMapper;
import com.gustavosass.orders.model.address.Address;
import com.gustavosass.orders.model.address.dto.AddressCreateDTO;
import com.gustavosass.orders.model.address.dto.AddressDTO;
import com.gustavosass.orders.model.address.dto.AddressUpdateDTO;
import com.gustavosass.orders.model.city.City;
import com.gustavosass.orders.model.city.dto.CityDTO;
import com.gustavosass.orders.model.country.Country;
import com.gustavosass.orders.model.country.dto.CountryDTO;
import com.gustavosass.orders.model.state.State;
import com.gustavosass.orders.model.state.dto.StateDTO;
import com.gustavosass.orders.repository.AddressRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private ViaCepClient viaCepClient;

    @Mock
    private CountryService countryService;

    @Mock
    private StateService stateService;

    @Mock
    private CityService cityService;

    @Mock
    private CountryMapper countryMapper;

    @Mock
    private StateMapper stateMapper;

    @Mock
    private CityMapper cityMapper;

    @InjectMocks
    private AddressService addressService;

    private Address address;
    private AddressDTO addressDTO;
    private AddressCreateDTO addressCreateDTO;
    private AddressUpdateDTO addressUpdateDTO;
    private City city;
    private CityDTO cityDTO;
    private State state;
    private StateDTO stateDTO;
    private Country country;
    private CountryDTO countryDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        country = Country.builder()
                .id(1L)
                .name("Test Country")
                .build();
                
        countryDTO = CountryDTO.builder()
                .id(1L)
                .name("Test Country")
                .build();

        state = State.builder()
                .id(1L)
                .name("Test State")
                .initials("TS")
                .country(country)
                .build();
                
        stateDTO = StateDTO.builder()
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
                .street("Updated Street")
                .number("456")
                .district("Updated District")
                .complement("Updated Complement")
                .postalCode("87654321")
                .build();
    }

    @Test
    @DisplayName("Find address by ID successfully")
    void whenFindByIdThenReturnAddress() {
        when(addressRepository.findById(any(Long.class))).thenReturn(Optional.of(address));
        when(addressMapper.toDTO(any(Address.class))).thenReturn(addressDTO);

        AddressDTO found = addressService.findById(1L);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(address.getId());
    }

    @Test
    @DisplayName("Throw exception when address ID not found")
    void whenFindByIdNotFoundThenThrowException() {
        when(addressRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class,
                () -> addressService.findById(1L));

        assertThat(exception.getMessage()).isEqualTo("Address not found");
    }

    @Test
    @DisplayName("Create address successfully")
    void whenCreateAddressThenSuccess() {
        when(countryService.findById(any(Long.class))).thenReturn(countryDTO);
        when(stateService.findByIdAndCountryId(any(Long.class), any(Long.class))).thenReturn(stateDTO);
        when(cityService.findByIdAndStateId(any(Long.class), any(Long.class))).thenReturn(cityDTO);
        when(countryMapper.toEntity(any(CountryDTO.class))).thenReturn(country);
        when(stateMapper.toEntity(any(StateDTO.class))).thenReturn(state);
        when(cityMapper.toEntity(any(CityDTO.class))).thenReturn(city);
        when(addressMapper.toEntity(any(AddressCreateDTO.class))).thenReturn(address);
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(addressMapper.toDTO(any(Address.class))).thenReturn(addressDTO);

        AddressDTO created = addressService.create(addressCreateDTO);

        assertThat(created).isNotNull();
        assertThat(created.getId()).isEqualTo(address.getId());
        verify(addressRepository).save(any(Address.class));
    }

    @Test
    @DisplayName("Update address successfully")
    void whenUpdateAddressThenSuccess() {
        Address updatedAddress = address;
        updatedAddress.setStreet("Updated Street");
        
        AddressDTO updatedAddressDTO = addressDTO;
        updatedAddressDTO.setStreet("Updated Street");

        when(addressRepository.findById(any(Long.class))).thenReturn(Optional.of(address));
        when(countryService.findById(any(Long.class))).thenReturn(countryDTO);
        when(stateService.findByIdAndCountryId(any(Long.class), any(Long.class))).thenReturn(stateDTO);
        when(cityService.findByIdAndStateId(any(Long.class), any(Long.class))).thenReturn(cityDTO);
        when(countryMapper.toEntity(any(CountryDTO.class))).thenReturn(country);
        when(stateMapper.toEntity(any(StateDTO.class))).thenReturn(state);
        when(cityMapper.toEntity(any(CityDTO.class))).thenReturn(city);
        when(addressMapper.toEntity(any(AddressUpdateDTO.class))).thenReturn(updatedAddress);
        when(addressRepository.save(any(Address.class))).thenReturn(updatedAddress);
        when(addressMapper.toDTO(any(Address.class))).thenReturn(updatedAddressDTO);

        AddressDTO result = addressService.update(1L, addressUpdateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getStreet()).isEqualTo("Updated Street");
        verify(addressRepository).save(any(Address.class));
    }
} 