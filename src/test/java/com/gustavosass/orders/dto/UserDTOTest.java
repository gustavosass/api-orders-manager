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

class UserDTOTest {
    
    private Validator validator;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        userDTO = UserDTO.builder()
                .id(1L)
                .name("Test User")
                .email("test@test.com")
                .role(RoleEnum.USER)
                .build();
    }

    @Test
    @DisplayName("Deve validar quando todos os campos são válidos")
    void whenAllFieldsValidThenValidateSuccess() {
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Deve criar UserDTO usando construtor")
    void whenCreateUsingConstructorThenSuccess() {
        UserDTO dto = new UserDTO(1L, "Test User", "test@test.com", RoleEnum.USER);
        
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Test User");
        assertThat(dto.getEmail()).isEqualTo("test@test.com");
        assertThat(dto.getRole()).isEqualTo(RoleEnum.USER);
    }

    @Test
    @DisplayName("Deve criar UserDTO usando builder")
    void whenCreateUsingBuilderThenSuccess() {
        UserDTO dto = UserDTO.builder()
                .id(1L)
                .name("Test User")
                .email("test@test.com")
                .role(RoleEnum.USER)
                .build();

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Test User");
        assertThat(dto.getEmail()).isEqualTo("test@test.com");
        assertThat(dto.getRole()).isEqualTo(RoleEnum.USER);
    }

    @Test
    @DisplayName("Deve falhar na validação quando nome é nulo")
    void whenNameIsNullThenValidationFails() {
        userDTO.setName(null);
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Nome é obrigatório");
    }

    @Test
    @DisplayName("Deve falhar na validação quando email é inválido")
    void whenEmailIsInvalidThenValidationFails() {
        userDTO.setEmail("invalid-email");
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Email inválido");
    }

    @Test
    @DisplayName("Deve falhar na validação quando email é nulo")
    void whenEmailIsNullThenValidationFails() {
        userDTO.setEmail(null);
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Email é obrigatório");
    }

    @Test
    @DisplayName("Deve falhar na validação quando role é nulo")
    void whenRoleIsNullThenValidationFails() {
        userDTO.setRole(null);
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Permissão é obrigatória");
    }
}