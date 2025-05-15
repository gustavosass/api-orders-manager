package com.gustavosass.orders.dto.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gustavosass.orders.model.user.dto.PasswordDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class PasswordDTOTest {
    
    private Validator validator;
    private PasswordDTO passwordDTO;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        passwordDTO = new PasswordDTO("newPassword123");
    }

    @Test
    @DisplayName("Deve validar quando a senha é válida")
    void whenPasswordIsValidThenValidateSuccess() {
        Set<ConstraintViolation<PasswordDTO>> violations = validator.validate(passwordDTO);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Deve falhar na validação quando a senha é nula")
    void whenPasswordIsNullThenValidationFails() {
        passwordDTO.setPassword(null);
        Set<ConstraintViolation<PasswordDTO>> violations = validator.validate(passwordDTO);
        assertThat(violations).hasSize(2);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder("Senha não pode ser nula", "Senha não pode estar em branco");
    }

    @Test
    @DisplayName("Deve falhar na validação quando a senha está em branco")
    void whenPasswordIsBlankThenValidationFails() {
        passwordDTO.setPassword("");
        Set<ConstraintViolation<PasswordDTO>> violations = validator.validate(passwordDTO);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Senha não pode estar em branco");
    }
} 