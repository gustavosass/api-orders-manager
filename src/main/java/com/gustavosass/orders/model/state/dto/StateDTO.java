package com.gustavosass.orders.model.state.dto;

import com.gustavosass.orders.model.country.dto.CountryDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StateDTO {
   private Long id;
   private String name;
   private String initials;
   private CountryDTO countryDTO;
}
