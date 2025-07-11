package com.gustavosass.orders.dto.city;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gustavosass.orders.dto.CityDTO;
import com.gustavosass.orders.dto.CountryDTO;
import com.gustavosass.orders.dto.StateDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class CityDTOTest {
    
    private Validator validator;
    private CityDTO cityDTO;
    private StateDTO stateDTO;
    private CountryDTO countryDTO;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        
        countryDTO = CountryDTO.builder()
                .id(1L)
                .name("Test Country")
                .build();
                
        stateDTO = StateDTO.builder()
                .id(1L)
                .name("Test State")
                .initials("TS")
                .countryDTO(countryDTO)
                .build();
                
        cityDTO = CityDTO.builder()
                .id(1L)
                .name("Test City")
                .stateDTO(stateDTO)
                .build();
    }

    @Test
    @DisplayName("Deve validar quando todos os campos são válidos")
    void whenAllFieldsValidThenValidateSuccess() {
        Set<ConstraintViolation<CityDTO>> violations = validator.validate(cityDTO);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Deve criar CityDTO usando builder")
    void whenCreateUsingBuilderThenSuccess() {
        CityDTO dto = CityDTO.builder()
                .id(1L)
                .name("Test City")
                .stateDTO(stateDTO)
                .build();

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Test City");
        assertThat(dto.getStateDTO()).isEqualTo(stateDTO);
    }
} 