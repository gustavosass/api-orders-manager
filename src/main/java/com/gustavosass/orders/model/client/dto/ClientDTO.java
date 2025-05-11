package com.gustavosass.orders.model.client.dto;

import java.util.Date;

import com.gustavosass.orders.model.address.dto.AddressDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDTO {
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    private String name;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;
    
    private Date birthDate;
    
    private String phone;
    
    @NotBlank(message = "Documento é obrigatório")
    private String document; 

    private AddressDTO addressDTO;
}