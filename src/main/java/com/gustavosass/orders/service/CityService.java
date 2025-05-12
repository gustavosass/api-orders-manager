package com.gustavosass.orders.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.gustavosass.orders.integration.viacep.ViaCepClient;
import com.gustavosass.orders.mapper.CityMapper;
import com.gustavosass.orders.model.city.City;
import com.gustavosass.orders.model.city.dto.CityDTO;
import com.gustavosass.orders.model.state.dto.StateDTO;
import com.gustavosass.orders.repository.CityRepository;

@Service
public class CityService {

   @Autowired
   private ViaCepClient viaCepClient;

   @Autowired
   private CityRepository cityRepository;

   @Autowired
   private StateService stateService;

   @Autowired
   private CityMapper cityMapper;

   public CityDTO findById(Long id) {
      return cityMapper.toDTO(cityRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("City not found")));
   }

   public List<CityDTO> findAll() {
      return cityRepository.findAll().stream().map(cityMapper::toDTO).toList();
   }

   public List<City> findByStateId(Long stateId) {
      return cityRepository.findByStateId(stateId);
   }

   public boolean existsByName(String name) {
      return cityRepository.existsByName(name);
   }

   public City findByIdAndStateId(Long id, Long stateId) {
      return cityRepository.findByIdAndStateId(id, stateId);
   }

   public CityDTO create(CityDTO cityDTO) {

      validateCity(cityDTO);

      StateDTO stateDb = stateService.findById(cityDTO.getStateDTO().getId());
      cityDTO.setStateDTO(stateDb);
      City city = cityMapper.toEntity(cityDTO);

      if (cityRepository.findByNameAndStateId(city.getName(), city.getState().getId()).isPresent()) {
         throw new DuplicateKeyException("City already exist");
      }

      return cityMapper.toDTO(
            cityRepository.save(city));
   }

   public CityDTO update(Long id, CityDTO cityDTO) {

      if (id == null) {
         throw new IllegalArgumentException("Id cannot be null");
      }
      validateCity(cityDTO);

      findById(id);
      cityDTO.setId(id);

      StateDTO stateDb = stateService.findById(cityDTO.getStateDTO().getId());
      cityDTO.setStateDTO(stateDb);
      City city = cityMapper.toEntity(cityDTO);

      if (cityRepository.findByNameAndStateId(city.getName(), city.getState().getId()).isPresent()) {
         throw new DuplicateKeyException("City already exist");
      }

      return cityMapper.toDTO(
            cityRepository.save(city));
   }

   public void delete(Long id) {
      findById(id);
      cityRepository.deleteById(id);
   }

   private void validateCity(CityDTO cityDTO) {
      if (cityDTO == null) {
         throw new IllegalArgumentException("City cannot be null");
      }

      if (cityDTO.getName() == null) {
         throw new IllegalArgumentException("City name cannot be null");
      }

      if (cityDTO.getStateDTO().getId() == null) {
         throw new IllegalArgumentException("State cannot be null");
      }
   }

}
