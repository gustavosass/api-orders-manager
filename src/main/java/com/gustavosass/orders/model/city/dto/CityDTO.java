package com.gustavosass.orders.model.city.dto;

import com.gustavosass.orders.model.state.dto.StateDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CityDTO {
   private long id;
   private String name;
   private StateDTO stateDTO;   
}
