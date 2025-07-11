package com.gustavosass.orders.dto.address;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gustavosass.orders.dto.AddressDTO;
import com.gustavosass.orders.model.City;
import com.gustavosass.orders.dto.CityDTO;
import com.gustavosass.orders.model.Country;
import com.gustavosass.orders.dto.CountryDTO;
import com.gustavosass.orders.model.State;
import com.gustavosass.orders.dto.StateDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class AddressDTOTest {

    private Validator validator;
    private AddressDTO addressDTO;
    private City city;
    private CityDTO cityDTO;
    private State state;
    private StateDTO stateDTO;
    private Country country;
    private CountryDTO countryDTO;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        
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

        addressDTO = AddressDTO.builder()
                .cityDTO(cityDTO)
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
    @DisplayName("Null postal code should not cause violation")
    void whenEmptyPostalCodeThenViolation() {
        addressDTO.setPostalCode(null);
        
        Set<ConstraintViolation<AddressDTO>> violations = validator.validate(addressDTO);
        
        assertThat(violations).isEmpty();
    }
} 