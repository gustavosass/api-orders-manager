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

import com.gustavosass.orders.dto.CityDTO;
import com.gustavosass.orders.dto.CreateCityDTO;
import com.gustavosass.orders.mapper.CityMapper;
import com.gustavosass.orders.mapper.StateMapper;
import com.gustavosass.orders.model.City;
import com.gustavosass.orders.model.State;
import com.gustavosass.orders.service.CityService;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/city")
@Tag(name = "Cidades", description = "Operações relacionadas às cidades")
public class CityController {

   @Autowired
   private CityService cityService;
   @Autowired
   private CityMapper cityMapper;
   @Autowired
   private StateMapper stateMapper;

   @GetMapping
   public ResponseEntity<List<CityDTO>> findAll() {
      List<CityDTO> cities = cityService.findAll().stream().map(cityMapper::toDTO).toList();
      return ResponseEntity.ok(cities);
   }

   @GetMapping("/{id}")
   public ResponseEntity<CityDTO> findById(@PathVariable Long id) {
      return ResponseEntity.ok(cityMapper.toDTO(cityService.findById(id)));
   }

   @PostMapping
   public ResponseEntity<CityDTO> create(@RequestBody @Valid CreateCityDTO createCityDTO) {
      if (createCityDTO.getIdState() == null) {
         return ResponseEntity.badRequest().build();
      }
      State state = new State();
      state.setId(createCityDTO.getIdState());
      City city = City.builder()
          .name(createCityDTO.getName())
          .state(state)
          .build();
      City created = cityService.create(city);
      return ResponseEntity.ok(cityMapper.toDTO(created));
   }

   @PutMapping("/{id}")
   public ResponseEntity<CityDTO> update(@PathVariable Long id, @RequestBody CityDTO cityDTO) {
      if (cityDTO.getStateDTO() == null || cityDTO.getStateDTO().getId() == null) {
         return ResponseEntity.badRequest().build();
      }
      State state = stateMapper.toEntity(cityDTO.getStateDTO());
      City city = cityMapper.toEntity(cityDTO);
      city.setState(state);
      City updated = cityService.update(id, city);
      if (updated == null) {
         return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(cityMapper.toDTO(updated));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> delete(@PathVariable Long id) {
      cityService.delete(id);
      return ResponseEntity.noContent().build();
   }

}
