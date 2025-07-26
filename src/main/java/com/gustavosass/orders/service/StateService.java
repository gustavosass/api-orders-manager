package com.gustavosass.orders.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.gustavosass.orders.model.State;
import com.gustavosass.orders.repository.StateRepository;

@Service
public class StateService {

   @Autowired
   private StateRepository stateRepository;

   @Autowired
   private CountryService countryService;

   public State findByName(String name) {
      return stateRepository.findByName(name);
   }

   public State findById(Long id) {
      return stateRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("State not found"));
   }

   public List<State> findAll() {
      return stateRepository.findAll();
   }

   public boolean existsByName(String name) {
      return stateRepository.existsByName(name);
   }

   public State findByIdAndCountryId(Long id, Long countryId) {
      return stateRepository.findByIdAndCountryId(id, countryId)
            .orElseThrow(() -> new NoSuchElementException("State not found"));
   }

   public State create(State state) {

      state.setCountry(countryService.findById(state.getCountry().getId()));

      if (stateRepository.findByName(state.getName()) != null) {
         throw new DuplicateKeyException("State already exist");
      }

      return stateRepository.save(state);
   }

   public State update(Long id, State state) {
      if (id == null) {
         throw new IllegalArgumentException("Id cannot be null");
      }
      State stateDb = stateRepository.findById(id).orElse(null);
      if (stateDb == null) {
         throw new NoSuchElementException("State not found");
      }
      state.setId(id);
      if (stateRepository.findByName(state.getName()) != null) {
         throw new DuplicateKeyException("State already exist");
      }
      return stateRepository.save(state);
   }

   public void delete(Long id) {
      findById(id);

      stateRepository.deleteById(id);
   }

}
