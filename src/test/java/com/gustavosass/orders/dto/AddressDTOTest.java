package com.gustavosass.orders.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gustavosass.orders.model.City;
import com.gustavosass.orders.model.State;
import com.gustavosass.orders.model.Country;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class AddressDTOTest {

    private Validator validator;
    private AddressDTO addressDTO;
    private City city;
    private State state;
    private Country country;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        
        country = Country.builder()
                .id(1L)
                .name("Test Country")
                .build();

        state = State.builder()
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

        addressDTO = AddressDTO.builder()
                .city(city)
                .street("Test Street")
                .number("123")
                .district("Test District")
                .complement("Test Complement")
                .postalCode("12345678")
                .build();
    }

    @Test
    @DisplayName("Valid DTO should not have violations")
    void whenValidDTOThenNoViolations() {
        Set<ConstraintViolation<AddressDTO>> violations = validator.validate(addressDTO);
        assertThat(violations).isEmpty();
    }


    @Test
    @DisplayName("Empty complement should not cause violation")
    void whenEmptyComplementThenNoViolation() {
        addressDTO.setComplement("");
        
        Set<ConstraintViolation<AddressDTO>> violations = validator.validate(addressDTO);
        
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Empty district should not cause violation")
    void whenEmptyDistrictThenNoViolation() {
        addressDTO.setDistrict("");
        
        Set<ConstraintViolation<AddressDTO>> violations = validator.validate(addressDTO);
        
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Empty number should not cause violation")
    void whenEmptyNumberThenNoViolation() {
        addressDTO.setNumber("");
        
        Set<ConstraintViolation<AddressDTO>> violations = validator.validate(addressDTO);
        
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Empty postal code should not cause violation")
    void whenEmptyPostalCodeThenViolation() {
        addressDTO.setPostalCode("");
        
        Set<ConstraintViolation<AddressDTO>> violations = validator.validate(addressDTO);
        
        assertThat(violations).isEmpty();
    }
}