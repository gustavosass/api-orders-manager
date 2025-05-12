package com.gustavosass.orders.model.address.dto;

import com.gustavosass.orders.model.city.dto.CityDTO;

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
    
    private String postalCode;
    
    private CityDTO cityDTO;

}
