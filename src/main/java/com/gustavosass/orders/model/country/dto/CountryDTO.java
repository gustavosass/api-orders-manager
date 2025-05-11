package com.gustavosass.orders.model.country.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CountryDTO {

   private Long id;
   private String name;
}
