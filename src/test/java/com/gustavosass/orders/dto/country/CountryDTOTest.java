package com.gustavosass.orders.dto.country;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gustavosass.orders.dto.CountryDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class CountryDTOTest {
    
    private Validator validator;
    private CountryDTO countryDTO;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        countryDTO = CountryDTO.builder()
                .id(1L)
                .name("Test Country")
                .build();
    }

    @Test
    @DisplayName("Deve validar quando todos os campos são válidos")
    void whenAllFieldsValidThenValidateSuccess() {
        Set<ConstraintViolation<CountryDTO>> violations = validator.validate(countryDTO);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Deve criar CountryDTO usando builder")
    void whenCreateUsingBuilderThenSuccess() {
        CountryDTO dto = CountryDTO.builder()
                .id(1L)
                .name("Test Country")
                .build();

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Test Country");
    }
} 