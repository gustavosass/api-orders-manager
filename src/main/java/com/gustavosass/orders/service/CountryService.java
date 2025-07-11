package com.gustavosass.orders.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.gustavosass.orders.mapper.CountryMapper;
import com.gustavosass.orders.model.Country;
import com.gustavosass.orders.dto.CountryDTO;
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

   public boolean existsByName(String name) {
      return countryRepository.existsByName(name);
   }

   public CountryDTO create(CountryDTO countryDTO) {

      if (countryRepository.findByName(countryDTO.getName()).isPresent()){
         throw new DuplicateKeyException("Country already exist");
      }

      Country country = Country.builder()
            .name(countryDTO.getName())
            .build();
      return countryMapper.toDTO(countryRepository.save(country));
   }

   public CountryDTO update(Long id, CountryDTO countryDTO) {

      if (id == null || countryDTO == null || countryDTO.getName() == null || countryDTO.getName().isEmpty()) {
         throw new IllegalArgumentException("ID/Country cannout be null");
      }

      Country countryDb = countryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("ID not found"));

      if (countryRepository.findByName(countryDTO.getName()).isPresent()) {
         throw new DuplicateKeyException("Country already exist");
      }

      Country country = countryMapper.toEntity(countryDTO);
      country.setId(countryDb.getId());
      return countryMapper.toDTO(countryRepository.save(country));

   }

   public void delete(Long id) {

      findById(id);

      countryRepository.deleteById(id);
   }

   public Optional<CountryDTO> findByName(String name) {
      return Optional.ofNullable(countryMapper.toDTO(countryRepository.findByName(name).orElse(null)));
   }
}
