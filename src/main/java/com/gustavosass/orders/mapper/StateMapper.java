package com.gustavosass.orders.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gustavosass.orders.model.State;
import com.gustavosass.orders.dto.StateDTO;

@Component
public class StateMapper {

   @Autowired
   private CountryMapper countryMapper;

   public State toEntity(StateDTO stateDTO) {
      return State.builder()
            .id(stateDTO.getId())
            .name(stateDTO.getName())
            .initials(stateDTO.getInitials())
            .country(countryMapper.toEntity(stateDTO.getCountryDTO()))
            .build();
   }

   public StateDTO toDTO(State state) {
      return StateDTO.builder()
            .id(state.getId())
            .name(state.getName())
            .initials(state.getInitials())
            .countryDTO(countryMapper.toDTO(state.getCountry()))
            .build();
   }
}
