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
import com.gustavosass.orders.service.CityService;

@RestController
@RequestMapping("/api/v1/city")
public class CityController {

   @Autowired
   private CityService cityService;

   @GetMapping
   public ResponseEntity<List<CityDTO>> findAll(){
      return ResponseEntity.ok(cityService.findAll());
   }

   @GetMapping("/{id}")
   public ResponseEntity<CityDTO> findById(@PathVariable Long id){
      return ResponseEntity.ok(cityService.findById(id));
   }

   @PostMapping
   public ResponseEntity<CityDTO> create(@RequestBody CityDTO cityDTO){
      return ResponseEntity.ok(cityService.create(cityDTO));
   }

   @PutMapping("/{id}")
   public ResponseEntity<CityDTO> create(@PathVariable Long id, @RequestBody CityDTO cityDTO){
      return ResponseEntity.ok(cityService.update(id, cityDTO));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<?> delete(@PathVariable Long id){
      cityService.delete(id);
      return ResponseEntity.noContent().build();
   }

}
