package com.gustavosass.orders.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gustavosass.orders.model.City;
import com.gustavosass.orders.dto.CityDTO;
import com.gustavosass.orders.model.Country;
import com.gustavosass.orders.dto.CountryDTO;
import com.gustavosass.orders.model.State;
import com.gustavosass.orders.dto.StateDTO;

@SpringBootTest
class CityMapperTest {

    @Autowired
    private CityMapper cityMapper;
    
    @Autowired
    private StateMapper stateMapper;
    
    private City city;
    private CityDTO cityDTO;
    private State state;
    private StateDTO stateDTO;
    private Country country;
    private CountryDTO countryDTO;
    
    @BeforeEach
    void setUp() {
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
    @DisplayName("Converter City para CityDTO")
    void whenToDTOThenReturnCityDTO() {
        CityDTO mappedDTO = cityMapper.toDTO(city);
        
        assertThat(mappedDTO).isNotNull();
        assertThat(mappedDTO.getId()).isEqualTo(city.getId());
        assertThat(mappedDTO.getName()).isEqualTo(city.getName());
        assertThat(mappedDTO.getStateDTO()).isNotNull();
        assertThat(mappedDTO.getStateDTO().getId()).isEqualTo(city.getState().getId());
        assertThat(mappedDTO.getStateDTO().getName()).isEqualTo(city.getState().getName());
        assertThat(mappedDTO.getStateDTO().getInitials()).isEqualTo(city.getState().getInitials());
        assertThat(mappedDTO.getStateDTO().getCountryDTO()).isNotNull();
        assertThat(mappedDTO.getStateDTO().getCountryDTO().getId()).isEqualTo(city.getState().getCountry().getId());
        assertThat(mappedDTO.getStateDTO().getCountryDTO().getName()).isEqualTo(city.getState().getCountry().getName());
    }
    
    @Test
    @DisplayName("Converter CityDTO para City")
    void whenToEntityThenReturnCity() {
        City mappedEntity = cityMapper.toEntity(cityDTO);
        
        assertThat(mappedEntity).isNotNull();
        assertThat(mappedEntity.getId()).isEqualTo(cityDTO.getId());
        assertThat(mappedEntity.getName()).isEqualTo(cityDTO.getName());
        assertThat(mappedEntity.getState()).isNotNull();
        assertThat(mappedEntity.getState().getId()).isEqualTo(cityDTO.getStateDTO().getId());
        assertThat(mappedEntity.getState().getName()).isEqualTo(cityDTO.getStateDTO().getName());
        assertThat(mappedEntity.getState().getInitials()).isEqualTo(cityDTO.getStateDTO().getInitials());
        assertThat(mappedEntity.getState().getCountry()).isNotNull();
        assertThat(mappedEntity.getState().getCountry().getId()).isEqualTo(cityDTO.getStateDTO().getCountryDTO().getId());
        assertThat(mappedEntity.getState().getCountry().getName()).isEqualTo(cityDTO.getStateDTO().getCountryDTO().getName());
    }
} 