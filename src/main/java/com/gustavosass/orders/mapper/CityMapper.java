package com.gustavosass.orders.mapper;

import com.gustavosass.orders.dto.CreateCityDTO;
import com.gustavosass.orders.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gustavosass.orders.model.City;
import com.gustavosass.orders.dto.CityDTO;

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

   public City toEntity(CreateCityDTO createCityDTO, State state){
      return City.builder()
              .name(createCityDTO.getName())
              .state(state)
              .build();
   }
}
