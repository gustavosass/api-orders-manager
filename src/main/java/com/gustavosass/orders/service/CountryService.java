package com.gustavosass.orders.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gustavosass.orders.model.Country;
import com.gustavosass.orders.repository.CountryRepository;

@Service
public class CountryService {

   @Autowired
   private CountryRepository countryRepository;

   public Country findById(Long id) {
      return countryRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Country not found"));
   }

   public Country findByName(String name) {
      return countryRepository.findByName(name);
   }

   public Country create(Country country) {
      if (country.getName() == null || country.getName().isEmpty()) {
         throw new IllegalArgumentException("Country name cannot be null or empty");
      }

      Country countryDb = countryRepository.findByName(country.getName());

      if (countryDb != null) {
         return countryDb;
      }
      return countryRepository.save(country);
   }


}
