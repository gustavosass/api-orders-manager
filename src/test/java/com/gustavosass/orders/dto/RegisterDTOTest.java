package com.gustavosass.orders.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gustavosass.orders.enums.RoleEnum;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class RegisterDTOTest {
    
    private Validator validator;
    private RegisterDTO registerDTO;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        registerDTO = RegisterDTO.builder()
                .name("Test User")
                .email("test@test.com")
                .password("password123")
                .role(RoleEnum.USER)
                .build();
    }

    @Test
    @DisplayName("Deve validar quando todos os campos são válidos")
    void whenAllFieldsValidThenValidateSuccess() {
        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(registerDTO);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Deve falhar na validação quando o nome é nulo")
    void whenNameIsNullThenValidationFails() {
        registerDTO.setName(null);
        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(registerDTO);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O nome não pode ser nulo");
    }

    @Test
    @DisplayName("Deve falhar na validação quando o nome está em branco")
    void whenNameIsBlankThenValidationFails() {
        registerDTO.setName("");
        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(registerDTO);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O nome não pode estar em branco");
    }

    @Test
    @DisplayName("Deve falhar na validação quando o nome é muito curto")
    void whenNameIsTooShortThenValidationFails() {
        registerDTO.setName("ab");
        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(registerDTO);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O nome deve ter entre 3 e 255 caracteres");
    }

    @Test
    @DisplayName("Deve falhar na validação quando o email é inválido")
    void whenEmailIsInvalidThenValidationFails() {
        registerDTO.setEmail("invalid-email");
        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(registerDTO);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O formato do email é inválido");
    }

    @Test
    @DisplayName("Deve falhar na validação quando o email é nulo")
    void whenEmailIsNullThenValidationFails() {
        registerDTO.setEmail(null);
        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(registerDTO);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O email não pode ser nulo");
    }

    @Test
    @DisplayName("Deve falhar na validação quando o email está em branco")
    void whenEmailIsBlankThenValidationFails() {
        registerDTO.setEmail("");
        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(registerDTO);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O email não pode estar em branco");
    }

    @Test
    @DisplayName("Deve falhar na validação quando a senha é muito curta")
    void whenPasswordIsTooShortThenValidationFails() {
        registerDTO.setPassword("12345");
        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(registerDTO);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("A senha deve ter no mínimo 6 caracteres");
    }

    @Test
    @DisplayName("Deve falhar na validação quando a senha é nula")
    void whenPasswordIsNullThenValidationFails() {
        registerDTO.setPassword(null);
        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(registerDTO);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("A senha não pode ser nula");
    }

    @Test
    @DisplayName("Deve falhar na validação quando a senha está em branco")
    void whenPasswordIsBlankThenValidationFails() {
        registerDTO.setPassword("");
        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(registerDTO);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("A senha não pode estar em branco");
    }

    @Test
    @DisplayName("Deve falhar na validação quando o perfil é nulo")
    void whenRoleIsNullThenValidationFails() {
        registerDTO.setRole(null);
        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(registerDTO);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O perfil não pode ser nulo");
    }
}