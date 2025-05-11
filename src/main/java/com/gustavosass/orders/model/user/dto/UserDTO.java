package com.gustavosass.orders.model.user.dto;

import com.gustavosass.orders.enums.RoleEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDTO {

    private Long id;
    @NotNull(message = "Nome é obrigatório")
    private String name;
    @Email(message = "Email inválido")
    @NotNull(message = "Email é obrigatório")
    private String email;
    @NotNull(message = "Permissão é obrigatória")
    private RoleEnum role;
}
