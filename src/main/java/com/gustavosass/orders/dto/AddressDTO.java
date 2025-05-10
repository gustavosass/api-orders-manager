package com.gustavosass.orders.dto;

import com.gustavosass.orders.model.City;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressDTO {

    private Long id;
    
    private String street;
    
    private String number;
    
    private String district;
    
    private String complement;
    
    @Size(min = 8, max = 8, message = "CEP deve ter 8 dígitos")
    private String postalCode;

    private City city;

}
