package com.gustavosass.orders.model.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDTO {
    @NotNull(message = "O email não pode ser nulo")
    @NotBlank(message = "O email não pode estar em branco")
    @Email(message = "O formato do email é inválido")
    private String email;

    @NotNull(message = "A senha não pode ser nula")
    @NotBlank(message = "A senha não pode estar em branco")
    private String password;
}