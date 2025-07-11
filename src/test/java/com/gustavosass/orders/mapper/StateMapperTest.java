package com.gustavosass.orders.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gustavosass.orders.model.Country;
import com.gustavosass.orders.dto.CountryDTO;
import com.gustavosass.orders.model.State;
import com.gustavosass.orders.dto.StateDTO;

@SpringBootTest
class StateMapperTest {

    @Autowired
    private StateMapper stateMapper;
    
    @Autowired
    private CountryMapper countryMapper;
    
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
    }
    
    @Test
    @DisplayName("Converter State para StateDTO")
    void whenToDTOThenReturnStateDTO() {
        StateDTO mappedDTO = stateMapper.toDTO(state);
        
        assertThat(mappedDTO).isNotNull();
        assertThat(mappedDTO.getId()).isEqualTo(state.getId());
        assertThat(mappedDTO.getName()).isEqualTo(state.getName());
        assertThat(mappedDTO.getInitials()).isEqualTo(state.getInitials());
        assertThat(mappedDTO.getCountryDTO()).isNotNull();
        assertThat(mappedDTO.getCountryDTO().getId()).isEqualTo(state.getCountry().getId());
        assertThat(mappedDTO.getCountryDTO().getName()).isEqualTo(state.getCountry().getName());
    }
    
    @Test
    @DisplayName("Converter StateDTO para State")
    void whenToEntityThenReturnState() {
        State mappedEntity = stateMapper.toEntity(stateDTO);
        
        assertThat(mappedEntity).isNotNull();
        assertThat(mappedEntity.getId()).isEqualTo(stateDTO.getId());
        assertThat(mappedEntity.getName()).isEqualTo(stateDTO.getName());
        assertThat(mappedEntity.getInitials()).isEqualTo(stateDTO.getInitials());
        assertThat(mappedEntity.getCountry()).isNotNull();
        assertThat(mappedEntity.getCountry().getId()).isEqualTo(stateDTO.getCountryDTO().getId());
        assertThat(mappedEntity.getCountry().getName()).isEqualTo(stateDTO.getCountryDTO().getName());
    }
} 