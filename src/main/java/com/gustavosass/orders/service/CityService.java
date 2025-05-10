package com.gustavosass.orders.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gustavosass.orders.integration.viacep.ViaCepClient;
import com.gustavosass.orders.integration.viacep.ViaCepResponse;
import com.gustavosass.orders.model.City;
import com.gustavosass.orders.model.State;
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

   //Only create with CEP
   public City create(String postalCode) {
      if(postalCode == null || postalCode.isEmpty()) {
         throw new IllegalArgumentException("Postal code cannot be null or empty");
      }

      postalCode = postalCode.replaceAll("[^0-9]", "");
      
      if(postalCode.length() != 8) {
         throw new IllegalArgumentException("Postal code must be 8 digits");
      }

      ViaCepResponse viaCepResponse = viaCepClient.getAddressByPostalCode(postalCode)
            .orElseThrow(() -> new IllegalArgumentException("Postal code not found"));

      City cityDb = cityRepository.findByNameAndState(viaCepResponse.getLocalidade(), viaCepResponse.getEstado());

      if (cityDb != null) {
         return cityDb;
      }

      State state = State.builder()
            .name(viaCepResponse.getEstado())
            .initials(viaCepResponse.getUf())
            .build();

      state = stateService.create(state);

      City city = City.builder()
            .name(viaCepResponse.getLocalidade())
            .state(state)
            .build();

      return cityRepository.save(city);
   }

}
