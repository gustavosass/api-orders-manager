package com.gustavosass.orders.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

   public List<Country> findAll() {
      return countryRepository.findAll();
   }

   public boolean existsByName(String name) {
      return countryRepository.existsByName(name);
   }

   public Country create(Country country) {
      if (country.getName() == null || country.getName().isEmpty()) {
         throw new IllegalArgumentException("Country name must be provided");
      }
      if (countryRepository.findByName(country.getName()).isPresent()) {
         throw new DuplicateKeyException("Country already exist");
      }
      return countryRepository.save(country);
   }

   public Country update(Long id, Country country) {
      if (id == null || country == null || country.getName() == null || country.getName().isEmpty()) {
         throw new IllegalArgumentException("ID/Country cannot be null");
      }
      Country countryDb = countryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("ID not found"));
      if (countryRepository.findByName(country.getName()).isPresent()) {
         throw new DuplicateKeyException("Country already exist");
      }
      country.setId(countryDb.getId());
      return countryRepository.save(country);
   }

   public void delete(Long id) {

      findById(id);

      countryRepository.deleteById(id);
   }

   public Optional<Country> findByName(String name) {
      return Optional.ofNullable(countryRepository.findByName(name).orElse(null));
   }
}
