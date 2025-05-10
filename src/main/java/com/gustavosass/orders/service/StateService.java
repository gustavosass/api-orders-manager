package com.gustavosass.orders.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gustavosass.orders.model.Country;
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

   public State create(State state) {

      if (state.getName() == null || state.getName().isEmpty()) {
         throw new IllegalArgumentException("State name cannot be null or empty");
      }
   
      if (state.getCountry() == null || state.getCountry().getName() == null) {
         throw new IllegalArgumentException("Country cannot be null");
      }

      State stateDb = stateRepository.findByNameAndCountryName(state.getName(), state.getCountry().getName());
      if (stateDb != null) {
         return stateDb;
      }

      Country country = countryService.create(state.getCountry());

      state.setCountry(country);
      return stateRepository.save(state);
   }   
   
}
