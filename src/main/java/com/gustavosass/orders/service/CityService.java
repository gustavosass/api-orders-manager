package com.gustavosass.orders.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gustavosass.orders.integration.viacep.ViaCepClient;
import com.gustavosass.orders.integration.viacep.ViaCepResponse;
import com.gustavosass.orders.model.city.City;
import com.gustavosass.orders.model.state.State;
import com.gustavosass.orders.repository.CityRepository;

@Service
public class CityService {

   @Autowired
   private ViaCepClient viaCepClient;

   @Autowired
   private CityRepository cityRepository;

   @Autowired
   private StateService stateService;

   public City findById(Long id) {
      return cityRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("City not found"));
   }

   public List<City> findAll() {
      return cityRepository.findAll();
   }

   public List<City> findByStateId(Long stateId) {
      return cityRepository.findByStateId(stateId);
   }
/*
   public City create(City city) {

      if (city == null){
         throw new IllegalArgumentException("City cannot be null");
      }

      if (city.getName() == null){
         throw new IllegalArgumentException("City name cannot be null");
      }

      //State já existente
      if (state.getId() != null){
         State stateDb = stateRepository.getById(state.getId());

         if (stateDb != null){
            return stateDb;
         }
      }

      //Criar com o nome
      Country countryDb = countryService.create(state.getCountry());
      state.setCountry(countryDb);

      return stateRepository.findByNameAndCountryId(state.getName(), countryDb.getId()).orElseGet(() -> {
         state.setId(null);
         return stateRepository.save(state);  
      });
   }   
*/

}
