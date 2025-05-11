package com.gustavosass.orders.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gustavosass.orders.model.user.dto.AuthenticationRequestDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class AuthenticationRequestDTOTest {

    private Validator validator;
    private AuthenticationRequestDTO authRequestDTO;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        authRequestDTO = AuthenticationRequestDTO.builder()
                .email("test@test.com")
                .password("password123")
                .build();
    }

    @Test
    @DisplayName("Deve validar quando todos os campos são válidos")
    void whenAllFieldsValidThenValidateSuccess() {
        Set<ConstraintViolation<AuthenticationRequestDTO>> violations = validator.validate(authRequestDTO);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Deve criar AuthenticationRequestDTO usando builder")
    void whenCreateUsingBuilderThenSuccess() {
        AuthenticationRequestDTO dto = AuthenticationRequestDTO.builder()
                .email("test@test.com")
                .password("password123")
                .build();

        assertThat(dto.getEmail()).isEqualTo("test@test.com");
        assertThat(dto.getPassword()).isEqualTo("password123");
    }

    @Test
    @DisplayName("Deve criar AuthenticationRequestDTO usando construtor")
    void whenCreateUsingConstructorThenSuccess() {
        AuthenticationRequestDTO dto = new AuthenticationRequestDTO("test@test.com", "password123");

        assertThat(dto.getEmail()).isEqualTo("test@test.com");
        assertThat(dto.getPassword()).isEqualTo("password123");
    }

    @Test
    @DisplayName("Deve criar AuthenticationRequestDTO vazio e definir valores")
    void whenCreateEmptyAndSetValuesThenSuccess() {
        AuthenticationRequestDTO dto = new AuthenticationRequestDTO();
        dto.setEmail("test@test.com");
        dto.setPassword("password123");

        assertThat(dto.getEmail()).isEqualTo("test@test.com");
        assertThat(dto.getPassword()).isEqualTo("password123");
    }

    @Test
    @DisplayName("Deve falhar na validação quando o email é nulo")
    void whenEmailIsNullThenValidationFails() {
        authRequestDTO.setEmail(null);
        Set<ConstraintViolation<AuthenticationRequestDTO>> violations = validator.validate(authRequestDTO);
        assertThat(violations).hasSize(2);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
            .containsExactlyInAnyOrder("O email não pode ser nulo", "O email não pode estar em branco");
    }

    @Test
    @DisplayName("Deve falhar na validação quando o email está em branco")
    void whenEmailIsBlankThenValidationFails() {
        authRequestDTO.setEmail("");
        Set<ConstraintViolation<AuthenticationRequestDTO>> violations = validator.validate(authRequestDTO);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O email não pode estar em branco");
    }

    @Test
    @DisplayName("Deve falhar na validação quando o email é inválido")
    void whenEmailIsInvalidThenValidationFails() {
        authRequestDTO.setEmail("invalid-email");
        Set<ConstraintViolation<AuthenticationRequestDTO>> violations = validator.validate(authRequestDTO);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O formato do email é inválido");
    }

    @Test
    @DisplayName("Deve falhar na validação quando a senha é nula")
    void whenPasswordIsNullThenValidationFails() {
        authRequestDTO.setPassword(null);
        Set<ConstraintViolation<AuthenticationRequestDTO>> violations = validator.validate(authRequestDTO);
        assertThat(violations).hasSize(2);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
            .containsExactlyInAnyOrder("A senha não pode ser nula", "A senha não pode estar em branco");
    }

    @Test
    @DisplayName("Deve falhar na validação quando a senha está em branco")
    void whenPasswordIsBlankThenValidationFails() {
        authRequestDTO.setPassword("");
        Set<ConstraintViolation<AuthenticationRequestDTO>> violations = validator.validate(authRequestDTO);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("A senha não pode estar em branco");
    }
}