package com.gustavosass.orders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.gustavosass.orders.model.state.dto.StateDTO;
import com.gustavosass.orders.service.StateService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/v1/api/state")
public class StateController {

   @Autowired
   private StateService stateService;

   @GetMapping
   public ResponseEntity<List<StateDTO>> findAll(){
      return ResponseEntity.ok(stateService.findAll());
   }

   @GetMapping("/{id}")
   public ResponseEntity<StateDTO> findById(@PathVariable Long id){
      return ResponseEntity.ok(stateService.findById(id));
   }

   @PostMapping
   public ResponseEntity<StateDTO> create(@RequestBody StateDTO stateDTO){
      return ResponseEntity.ok(stateService.create(stateDTO));
   }

   @PutMapping("/{id}")
   public ResponseEntity<StateDTO> update(@PathVariable Long id, @RequestBody StateDTO stateDTO){
      return ResponseEntity.ok(stateService.update(id, stateDTO));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<?> delete(@PathVariable Long id){
      stateService.delete(id);
      return ResponseEntity.noContent().build();
   }
}
