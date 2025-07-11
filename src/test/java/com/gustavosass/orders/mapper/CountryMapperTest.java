package com.gustavosass.orders.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gustavosass.orders.model.Country;
import com.gustavosass.orders.dto.CountryDTO;

@SpringBootTest
class CountryMapperTest {

    @Autowired
    private CountryMapper countryMapper;
    
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
    }
    
    @Test
    @DisplayName("Converter Country para CountryDTO")
    void whenToDTOThenReturnCountryDTO() {
        CountryDTO mappedDTO = countryMapper.toDTO(country);
        
        assertThat(mappedDTO).isNotNull();
        assertThat(mappedDTO.getId()).isEqualTo(country.getId());
        assertThat(mappedDTO.getName()).isEqualTo(country.getName());
    }
    
    @Test
    @DisplayName("Converter CountryDTO para Country")
    void whenToEntityThenReturnCountry() {
        Country mappedEntity = countryMapper.toEntity(countryDTO);
        
        assertThat(mappedEntity).isNotNull();
        assertThat(mappedEntity.getId()).isEqualTo(countryDTO.getId());
        assertThat(mappedEntity.getName()).isEqualTo(countryDTO.getName());
    }
} 