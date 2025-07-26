package com.gustavosass.orders.dto;

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
    
    private Long cityId;
}
