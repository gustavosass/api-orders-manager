package com.gustavosass.orders.dto;

import com.gustavosass.orders.model.City;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressDTO {

    private Long id;
    
    private City city;
    
    private String street;
    
    private String number;
    
    private String district;
    
    private String complement;
    
    private String postalCode;
}
