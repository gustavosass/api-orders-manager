package com.gustavosass.orders.dto;

import com.gustavosass.orders.enums.RoleEnum;

import jakarta.validation.constraints.Email;
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
public class UserRegisterDTO {
    @NotNull
    @Size(min = 3, max = 255, message = "The size must be between 3 and 255 characters")
    private String name;
    
    @NotNull
    @Email
    private String email;
    
    @NotNull
    @Size(min = 6, message = "Must have at least 6 characters")
    private String password;
    
    @NotNull
    private RoleEnum role;
}
