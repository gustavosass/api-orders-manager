package com.gustavosass.orders.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gustavosass.orders.model.City;
import com.gustavosass.orders.model.Country;
import com.gustavosass.orders.model.State;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class ClientDTOTest {

    private Validator validator;
    private ClientDTO clientDTO;
    private City city;
    private State state;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        
        Country country = Country.builder()
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

        clientDTO = ClientDTO.builder()
                .id(1L)
                .name("Test Client")
                .email("test@test.com")
                .birthDate(new Date())
                .phone("123456789")
                .document("12345678900")
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
        Set<ConstraintViolation<ClientDTO>> violations = validator.validate(clientDTO);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Empty name should cause violation")
    void whenEmptyNameThenViolation() {
        clientDTO.setName("");
        Set<ConstraintViolation<ClientDTO>> violations = validator.validate(clientDTO);
        
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Nome é obrigatório");
    }

    @Test
    @DisplayName("Lançar violação de email inválido")
    void whenInvalidEmailThenViolation() {
        clientDTO.setEmail("invalid-email");
        Set<ConstraintViolation<ClientDTO>> violations = validator.validate(clientDTO);
        
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Email inválido");
    }

    @Test
    @DisplayName("Empty document should cause violation")
    void whenEmptyDocumentThenViolation() {
        clientDTO.setDocument("");
        Set<ConstraintViolation<ClientDTO>> violations = validator.validate(clientDTO);
        
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Document is required");
    }

    @Test
    @DisplayName("Empty complement should not cause violation")
    void whenEmptyComplementThenNoViolation() {
        clientDTO.setComplement("");
        Set<ConstraintViolation<ClientDTO>> violations = validator.validate(clientDTO);
        
        assertThat(violations).isEmpty();
    }
}