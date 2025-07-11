package com.gustavosass.orders.dto;

import com.gustavosass.orders.dto.CountryDTO;

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
