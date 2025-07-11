package com.gustavosass.orders.dto;

import java.util.Date;

import com.gustavosass.orders.dto.AddressCreateDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientCreateDTO {
    private Long id;
    
    @NotBlank
    private String name;
    
    @NotBlank
    @Email
    private String email;
    
    private Date birthDate;
    
    private String phone;
    
    @NotBlank
    private String document; 

    @NotNull
    private AddressCreateDTO addressCreateDTO;
}
