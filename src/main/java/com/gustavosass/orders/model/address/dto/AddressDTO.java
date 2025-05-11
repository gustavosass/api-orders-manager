package com.gustavosass.orders.model.address.dto;

import com.gustavosass.orders.model.city.dto.CityDTO;
import com.gustavosass.orders.model.country.dto.CountryDTO;
import com.gustavosass.orders.model.state.dto.StateDTO;

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
    
    private CityDTO cityDTO;
    private StateDTO stateDTO;
    private CountryDTO countryDTO;

}
