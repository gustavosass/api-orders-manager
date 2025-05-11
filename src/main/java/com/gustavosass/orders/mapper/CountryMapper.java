package com.gustavosass.orders.mapper;

import org.springframework.stereotype.Component;

import com.gustavosass.orders.model.country.Country;
import com.gustavosass.orders.model.country.dto.CountryDTO;

@Component
public class CountryMapper {

   public Country toEntity(CountryDTO countryDTO){
      return Country.builder()
            .id(countryDTO.getId())
            .name(countryDTO.getName())
            .build();
   }

   public CountryDTO toDTO(Country country){
      return CountryDTO.builder()
            .id(country.getId())
            .name(country.getName())
            .build();
   }

}
