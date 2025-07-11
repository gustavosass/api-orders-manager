package com.gustavosass.orders.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
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
import org.springframework.dao.DuplicateKeyException;

import com.gustavosass.orders.mapper.CityMapper;
import com.gustavosass.orders.model.City;
import com.gustavosass.orders.dto.CityDTO;
import com.gustavosass.orders.model.Country;
import com.gustavosass.orders.dto.CountryDTO;
import com.gustavosass.orders.model.State;
import com.gustavosass.orders.dto.StateDTO;
import com.gustavosass.orders.repository.CityRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private StateService stateService;

    @Mock
    private CityMapper cityMapper;

    @InjectMocks
    private CityService cityService;

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
    }

    @Test
    @DisplayName("Find city by ID successfully")
    void whenFindByIdThenReturnCity() {
        when(cityRepository.findById(any(Long.class))).thenReturn(Optional.of(city));
        when(cityMapper.toDTO(any(City.class))).thenReturn(cityDTO);

        CityDTO found = cityService.findById(1L);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(city.getId());
        assertThat(found.getName()).isEqualTo(city.getName());
    }

    @Test
    @DisplayName("Throw exception when city ID not found")
    void whenFindByIdNotFoundThenThrowException() {
        when(cityRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class,
                () -> cityService.findById(1L));

        assertThat(exception.getMessage()).isEqualTo("City not found");
    }

    @Test
    @DisplayName("Find all cities successfully")
    void whenFindAllThenReturnCityList() {
        when(cityRepository.findAll()).thenReturn(Arrays.asList(city));
        when(cityMapper.toDTO(any(City.class))).thenReturn(cityDTO);

        List<CityDTO> cities = cityService.findAll();

        assertThat(cities).isNotEmpty();
        assertThat(cities).hasSize(1);
        assertThat(cities.get(0).getId()).isEqualTo(city.getId());
        assertThat(cities.get(0).getName()).isEqualTo(city.getName());
    }

    @Test
    @DisplayName("Find city by ID and state ID successfully")
    void whenFindByIdAndStateIdThenReturnCity() {
        when(cityRepository.findByIdAndStateId(any(Long.class), any(Long.class))).thenReturn(Optional.of(city));
        when(cityMapper.toDTO(any(City.class))).thenReturn(cityDTO);

        CityDTO found = cityService.findByIdAndStateId(1L, 1L);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(city.getId());
        assertThat(found.getName()).isEqualTo(city.getName());
    }

    @Test
    @DisplayName("Throw exception when city ID and state ID not found")
    void whenFindByIdAndStateIdNotFoundThenThrowException() {
        when(cityRepository.findByIdAndStateId(any(Long.class), any(Long.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class,
                () -> cityService.findByIdAndStateId(1L, 1L));

        assertThat(exception.getMessage()).isEqualTo("City not found");
    }

    @Test
    @DisplayName("Create city successfully")
    void whenCreateCityThenSuccess() {
        when(stateService.findById(any(Long.class))).thenReturn(stateDTO);
        when(cityMapper.toEntity(any(CityDTO.class))).thenReturn(city);
        when(cityRepository.findByNameAndStateId(any(String.class), any(Long.class))).thenReturn(Optional.empty());
        when(cityRepository.save(any(City.class))).thenReturn(city);
        when(cityMapper.toDTO(any(City.class))).thenReturn(cityDTO);

        CityDTO created = cityService.create(cityDTO);

        assertThat(created).isNotNull();
        assertThat(created.getId()).isEqualTo(city.getId());
        assertThat(created.getName()).isEqualTo(city.getName());
        verify(cityRepository).save(any(City.class));
    }

    @Test
    @DisplayName("Throw exception when creating duplicate city")
    void whenCreateDuplicateCityThenThrowException() {
        when(stateService.findById(any(Long.class))).thenReturn(stateDTO);
        when(cityMapper.toEntity(any(CityDTO.class))).thenReturn(city);
        when(cityRepository.findByNameAndStateId(any(String.class), any(Long.class))).thenReturn(Optional.of(city));

        Exception exception = assertThrows(DuplicateKeyException.class,
                () -> cityService.create(cityDTO));

        assertThat(exception.getMessage()).isEqualTo("City already exist");
    }

    @Test
    @DisplayName("Throw exception when creating city with null data")
    void whenCreateCityWithNullDataThenThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> cityService.create(null));

        assertThat(exception.getMessage()).isEqualTo("City cannot be null");
    }

    @Test
    @DisplayName("Update city successfully")
    void whenUpdateCityThenSuccess() {
        City updatedCity = city;
        updatedCity.setName("Updated City");
        
        CityDTO updatedCityDTO = cityDTO;
        updatedCityDTO.setName("Updated City");

        when(cityRepository.findById(any(Long.class))).thenReturn(Optional.of(city));
        when(stateService.findById(any(Long.class))).thenReturn(stateDTO);
        when(cityMapper.toEntity(any(CityDTO.class))).thenReturn(updatedCity);
        when(cityRepository.findByNameAndStateId(any(String.class), any(Long.class))).thenReturn(Optional.empty());
        when(cityRepository.save(any(City.class))).thenReturn(updatedCity);
        when(cityMapper.toDTO(any(City.class))).thenReturn(updatedCityDTO);

        CityDTO result = cityService.update(1L, updatedCityDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated City");
        verify(cityRepository).save(any(City.class));
    }

    @Test
    @DisplayName("Throw exception when updating city with null ID")
    void whenUpdateCityWithNullIdThenThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> cityService.update(null, cityDTO));

        assertThat(exception.getMessage()).isEqualTo("Id cannot be null");
    }

    @Test
    @DisplayName("Delete city successfully")
    void whenDeleteCityThenSuccess() {
        when(cityRepository.findById(any(Long.class))).thenReturn(Optional.of(city));
        when(cityMapper.toDTO(any(City.class))).thenReturn(cityDTO);

        cityService.delete(1L);

        verify(cityRepository).deleteById(any(Long.class));
    }
} 