package com.gustavosass.orders.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.gustavosass.orders.mapper.CountryMapper;
import com.gustavosass.orders.model.country.Country;
import com.gustavosass.orders.model.country.dto.CountryDTO;
import com.gustavosass.orders.repository.CountryRepository;

@Service
public class CountryService {

   @Autowired
   private CountryRepository countryRepository;

   @Autowired
   private CountryMapper countryMapper;

   public CountryDTO findById(Long id) {
      return countryMapper.toDTO(
            countryRepository.findById(id)
                  .orElseThrow(() -> new IllegalArgumentException("Country not found")));
   }

   public List<CountryDTO> findAll() {
      return countryRepository.findAll().stream()
            .map(countryMapper::toDTO)
            .toList();
   }

   public CountryDTO create(CountryDTO countryDTO) {
      return countryMapper.toDTO(countryRepository.findByName(countryDTO.getName()).orElseGet(() -> {
         Country country = Country.builder()
               .name(countryDTO.getName())
               .build();
         return countryRepository.save(country);
      }));
   }

   public CountryDTO update(Long id, CountryDTO countryDTO) {

      if (id == null || countryDTO == null || countryDTO.getName() == null || countryDTO.getName().isEmpty()) {
         throw new IllegalArgumentException("ID/Country cannout be null");
      }

      Country countryDb = countryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("ID not found"));

      if (countryRepository.findByName(countryDTO.getName()).isPresent()){
         throw new DuplicateKeyException("Country already exist");
      }

      Country country = countryMapper.toEntity(countryDTO);
      country.setId(countryDb.getId());
      return countryMapper.toDTO(countryRepository.save(country));

   }

   public void delete(Long id) {

      countryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("ID not found"));

      countryRepository.deleteById(id);
   }
}
