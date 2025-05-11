package com.gustavosass.orders.mapper;

import org.springframework.stereotype.Component;

import com.gustavosass.orders.model.state.State;
import com.gustavosass.orders.model.state.dto.StateDTO;

@Component
public class StateMapper {

   public State toEntity(StateDTO stateDTO) {
      return State.builder()
            .id(stateDTO.getId())
            .name(stateDTO.getName())
            .initials(stateDTO.getInitials())
            .build();
   }

   public StateDTO toDTO(State state) {
      return StateDTO.builder()
            .id(state.getId())
            .name(state.getName())
            .initials(state.getInitials())
            .build();
   }
}
