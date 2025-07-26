package com.gustavosass.orders.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.gustavosass.orders.model.City;
import com.gustavosass.orders.repository.CityRepository;

@Service
public class CityService {

   @Autowired
   private CityRepository cityRepository;

   @Autowired
   private StateService stateService;

   public List<City> findAll() {
      return cityRepository.findAll();
   }

   public City findById(Long id) {
      return cityRepository.findById(id).orElse(null);
   }

   public List<City> findByStateId(Long stateId) {
      return cityRepository.findByStateId(stateId);
   }

   public boolean existsByName(String name) {
      return cityRepository.existsByName(name);
   }

   public City findByIdAndStateId(Long id, Long stateId) {
      return cityRepository.findByIdAndStateId(id, stateId)
            .orElseThrow(() -> new NoSuchElementException("City not found"));
   }

   public City create(City city) {

      if (cityRepository.findByNameAndStateId(city.getName(), city.getState().getId()).isPresent()) {
         throw new DuplicateKeyException("City already exist");
      }

      city.setState(stateService.findById(city.getState().getId()));

      return cityRepository.save(city);
   }

   public City update(Long id, City city) {

      if (id == null) {
         throw new IllegalArgumentException("Id cannot be null");
      }
      City cityDb = cityRepository.findById(id).orElse(null);
      if (cityDb == null) {
         throw new NoSuchElementException("City not found");
      }
      city.setId(id);
      if (cityRepository.findByNameAndStateId(city.getName(), city.getState().getId()).isPresent()) {
         throw new DuplicateKeyException("City already exist");
      }
      return cityRepository.save(city);
   }

   public void delete(Long id) {
      findById(id);
      cityRepository.deleteById(id);
   }
}
