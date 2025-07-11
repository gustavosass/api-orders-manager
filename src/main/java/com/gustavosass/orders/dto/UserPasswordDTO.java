package com.gustavosass.orders.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordDTO {
    @NotNull(message = "Senha não pode ser nula")
    @NotBlank(message = "Senha não pode estar em branco")
    private String password;
}
