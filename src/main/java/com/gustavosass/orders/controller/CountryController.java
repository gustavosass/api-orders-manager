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
import com.gustavosass.orders.mapper.CountryMapper;
import com.gustavosass.orders.model.Country;
import com.gustavosass.orders.dto.CreateCountryDTO;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/country")
@Tag(name = "Países", description = "Operações relacionadas aos países")
public class CountryController {

   @Autowired
   private CountryService countryService;

   @Autowired
   private CountryMapper countryMapper;

   @GetMapping
   public ResponseEntity<List<CountryDTO>> findAll() {
      List<Country> countries = countryService.findAll();
      List<CountryDTO> dtos = countries.stream().map(countryMapper::toDTO).toList();
      return ResponseEntity.ok(dtos);
   }

   @GetMapping("/{id}")
   public ResponseEntity<CountryDTO> findById(@PathVariable Long id) {
      Country country = countryService.findById(id);
      if (country == null) {
         return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(countryMapper.toDTO(country));
   }

   @PostMapping
   public ResponseEntity<CountryDTO> create(@RequestBody @Valid CreateCountryDTO createCountryDTO) {
      Country country = Country.builder()
         .name(createCountryDTO.getName())
         .build();
      Country created = countryService.create(country);
      return ResponseEntity.ok(countryMapper.toDTO(created));
   }

   @PutMapping("/{id}")
   public ResponseEntity<CountryDTO> update(@PathVariable Long id, @RequestBody @Valid CountryDTO countryDTO) {
      Country country = countryMapper.toEntity(countryDTO);
      Country updated = countryService.update(id, country);
      if (updated == null) {
         return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(countryMapper.toDTO(updated));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> delete(@PathVariable Long id) {
      countryService.delete(id);
      return ResponseEntity.noContent().build();
   }
}
