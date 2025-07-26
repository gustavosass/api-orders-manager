package com.gustavosass.orders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gustavosass.orders.dto.CreateStateDTO;
import com.gustavosass.orders.dto.StateDTO;
import com.gustavosass.orders.mapper.CountryMapper;
import com.gustavosass.orders.mapper.StateMapper;
import com.gustavosass.orders.model.Country;
import com.gustavosass.orders.model.State;
import com.gustavosass.orders.service.StateService;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/v1/api/state")
@Tag(name = "Estados", description = "Operações relacionadas aos estados")
public class StateController {

   @Autowired
   private StateService stateService;
   @Autowired
   private StateMapper stateMapper;
   @Autowired
   private CountryMapper countryMapper;

   @GetMapping
   public ResponseEntity<List<StateDTO>> findAll(){
      List<StateDTO> states = stateService.findAll().stream().map(stateMapper::toDTO).toList();
      return ResponseEntity.ok(states);
   }

   @GetMapping("/{id}")
   public ResponseEntity<StateDTO> findById(@PathVariable Long id){
      return ResponseEntity.ok(stateMapper.toDTO(stateService.findById(id)));
   }

   @PostMapping
   public ResponseEntity<StateDTO> create(@RequestBody @Valid CreateStateDTO createStateDTO) {
       if (createStateDTO.getIdCountry() == null) {
           return ResponseEntity.badRequest().build();
       }
       Country country = new Country();
       country.setId(createStateDTO.getIdCountry());
       State state = State.builder()
           .name(createStateDTO.getName())
           .initials(createStateDTO.getInitials())
           .country(country)
           .build();
       State created = stateService.create(state);
       return ResponseEntity.ok(stateMapper.toDTO(created));
   }

   @PutMapping("/{id}")
   public ResponseEntity<StateDTO> update(@PathVariable Long id, @RequestBody StateDTO stateDTO) {
       if (stateDTO.getCountryDTO() == null || stateDTO.getCountryDTO().getId() == null) {
           return ResponseEntity.badRequest().build();
       }
       Country country = countryMapper.toEntity(stateDTO.getCountryDTO());
       State state = stateMapper.toEntity(stateDTO);
       state.setCountry(country);
       State updated = stateService.update(id, state);
       if (updated == null) {
           return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok(stateMapper.toDTO(updated));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<?> delete(@PathVariable Long id){
      stateService.delete(id);
      return ResponseEntity.noContent().build();
   }
}
