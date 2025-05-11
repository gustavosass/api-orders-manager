package com.gustavosass.orders.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gustavosass.orders.model.city.City;
import com.gustavosass.orders.model.city.dto.CityDTO;

@Component
public class CityMapper {

   @Autowired
   private StateMapper stateMapper;

   public City toEntity(CityDTO cityDTO) {
      return City.builder()
            .id(cityDTO.getId())
            .name(cityDTO.getName())
            .state(stateMapper.toEntity(cityDTO.getStateDTO()))
            .build();
   }

   public CityDTO toDTO(City city){
      return CityDTO.builder()
               .id(city.getId())
               .name(city.getName())
               .stateDTO(stateMapper.toDTO(city.getState()))
               .build();
   }
}
