package com.gustavosass.orders.dto.state;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gustavosass.orders.dto.CountryDTO;
import com.gustavosass.orders.dto.StateDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class StateDTOTest {
    
    private Validator validator;
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
    }

    @Test
    @DisplayName("Deve validar quando todos os campos são válidos")
    void whenAllFieldsValidThenValidateSuccess() {
        Set<ConstraintViolation<StateDTO>> violations = validator.validate(stateDTO);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Deve criar StateDTO usando builder")
    void whenCreateUsingBuilderThenSuccess() {
        StateDTO dto = StateDTO.builder()
                .id(1L)
                .name("Test State")
                .initials("TS")
                .countryDTO(countryDTO)
                .build();

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Test State");
        assertThat(dto.getInitials()).isEqualTo("TS");
        assertThat(dto.getCountryDTO()).isEqualTo(countryDTO);
    }
} 