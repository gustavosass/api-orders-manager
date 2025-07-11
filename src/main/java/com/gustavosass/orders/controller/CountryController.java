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
import org.springframework.web.bind.annotation.RequestBody;

import com.gustavosass.orders.dto.CountryDTO;
import com.gustavosass.orders.service.CountryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {

   @Autowired
   private CountryService countryService;

   @GetMapping
   public ResponseEntity<List<CountryDTO>> findAll() {
      return ResponseEntity.ok(countryService.findAll());
   }

   @GetMapping("/{id}")
   public ResponseEntity<CountryDTO> findById(@PathVariable Long id) {
      return ResponseEntity.ok(countryService.findById(id));
   }

   @PostMapping
   public ResponseEntity<CountryDTO> create(@RequestBody @Valid CountryDTO countryDTO) {
      return ResponseEntity.ok(countryService.create(countryDTO));
   }

   @PutMapping("/{id}")
   public ResponseEntity<CountryDTO> update(@PathVariable Long id, @RequestBody @Valid CountryDTO countryDTO) {
      return ResponseEntity.ok(countryService.update(id, countryDTO));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> delete(@PathVariable Long id) {
      countryService.delete(id);
      return ResponseEntity.noContent().build();
   }
}
