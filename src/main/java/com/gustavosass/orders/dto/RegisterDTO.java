package com.gustavosass.orders.dto;

import com.gustavosass.orders.enums.RoleEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    @NotNull(message = "O nome não pode ser nulo")
    @NotBlank(message = "O nome não pode estar em branco")
    @Size(min = 3, max = 255, message = "O nome deve ter entre 3 e 255 caracteres")
    private String name;
    
    @NotNull(message = "O email não pode ser nulo")
    @NotBlank(message = "O email não pode estar em branco")
    @Email(message = "O formato do email é inválido")
    private String email;
    
    @NotNull(message = "A senha não pode ser nula")
    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String password;
    
    @NotNull(message = "O perfil não pode ser nulo")
    private RoleEnum role;
}
