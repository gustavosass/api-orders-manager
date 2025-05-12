package com.gustavosass.orders.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.gustavosass.orders.mapper.CountryMapper;
import com.gustavosass.orders.mapper.StateMapper;
import com.gustavosass.orders.model.country.Country;
import com.gustavosass.orders.model.state.State;
import com.gustavosass.orders.model.state.dto.StateDTO;
import com.gustavosass.orders.repository.StateRepository;

@Service
public class StateService {

   @Autowired
   private StateRepository stateRepository;

   @Autowired
   private StateMapper stateMapper;

   @Autowired
   private CountryService countryService;

   @Autowired
   private CountryMapper countryMapper;

   public State findByName(String name) {
      return stateRepository.findByName(name);
   }

   public StateDTO findById(Long id) {
      return stateMapper.toDTO(stateRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("State not found")));
   }

   public List<StateDTO> findAll(){
      return stateRepository.findAll().stream().map(stateMapper::toDTO).toList();
   }

   public boolean existsByName(String name) {
      return stateRepository.existsByName(name);
   }

   public State findByIdAndCountryId(Long id, Long countryId) {
      return stateRepository.findByIdAndCountryId(id, countryId);
   }

   public StateDTO create(StateDTO stateDTO) {

      if (stateDTO == null) {
         throw new IllegalArgumentException("State cannot be null");
      }

      if (stateDTO.getCountryDTO().getId() == null) {
         throw new IllegalArgumentException("Country ID cannot be null");
      }

      if (stateDTO.getName() == null) {
         throw new IllegalArgumentException("State name cannot be null");
      }

      State state = stateMapper.toEntity(stateDTO);

      if (stateRepository.findByNameAndCountryId(state.getName(), state.getCountry().getId()).isPresent()){
         throw new DuplicateKeyException("State already exist");
      }
         
      Country country = countryMapper.toEntity(
                     countryService.findById(state.getCountry().getId()));
      state.setCountry(country);
      return stateMapper.toDTO(stateRepository.save(state));
   }

   public StateDTO update(Long id, StateDTO stateDTO) {

      State stateDb = stateRepository.findById(id).orElseThrow(() -> new NoSuchElementException("ID not found"));

      if (stateDTO == null) {
         throw new IllegalArgumentException("State cannot be null");
      }

      State state = stateMapper.toEntity(stateDTO);
      state.setId(stateDb.getId());

      Country country = countryMapper.toEntity(countryService.findById(stateDTO.getCountryDTO().getId()));

      state.setCountry(country);

      if (stateRepository.findByNameAndCountryId(state.getName(), state.getCountry().getId()).isPresent()){
         throw new DuplicateKeyException("State already exist for Country");
      }
      
      return stateMapper.toDTO(state);
   }

   public void delete(Long id) {
      findById(id);

      stateRepository.deleteById(id);
   }

}
