package com.gustavosass.orders.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gustavosass.orders.integration.viacep.ViaCepClient;
import com.gustavosass.orders.mapper.AddressMapper;
import com.gustavosass.orders.mapper.CityMapper;
import com.gustavosass.orders.mapper.CountryMapper;
import com.gustavosass.orders.mapper.StateMapper;
import com.gustavosass.orders.model.address.Address;
import com.gustavosass.orders.model.address.dto.AddressCreateDTO;
import com.gustavosass.orders.model.address.dto.AddressDTO;
import com.gustavosass.orders.model.address.dto.AddressUpdateDTO;
import com.gustavosass.orders.model.city.City;
import com.gustavosass.orders.model.country.Country;
import com.gustavosass.orders.model.state.State;
import com.gustavosass.orders.repository.AddressRepository;

@Service
public class AddressService {

   @Autowired
   private AddressRepository addressRepository;

   @Autowired
   private AddressMapper addressMapper;

   @Autowired
   private ViaCepClient viaCepClient;

   @Autowired
   private CountryService countryService;

   @Autowired
   private StateService stateService;

   @Autowired
   private CityService cityService;

   @Autowired
   private CountryMapper countryMapper;

   @Autowired
   private StateMapper stateMapper;

   @Autowired
   private CityMapper cityMapper;

   public AddressDTO findById(Long id) {

      return addressMapper.toDTO(addressRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Address not found")));
   }

   public AddressDTO create(AddressCreateDTO addressCreateDTO) {

      City city = validateCityHierarchy(addressCreateDTO.getCountryId(), addressCreateDTO.getStateId(),
            addressCreateDTO.getCityId());

      Address address = addressMapper.toEntity(addressCreateDTO);
      address.setCity(city);

      return addressMapper.toDTO(
            addressRepository.save(address));
   }

   public AddressDTO update(Long id, AddressUpdateDTO addressUpdateDTO) {

      findById(id);

      City city = validateCityHierarchy(addressUpdateDTO.getCountryId(), addressUpdateDTO.getStateId(),
            addressUpdateDTO.getCityId());

      Address address = addressMapper.toEntity(addressUpdateDTO);
      address.setId(id);
      address.setCity(city);
      return addressMapper.toDTO(addressRepository.save(address));
   }

   private City validateCityHierarchy(Long countryId, Long stateId, Long cityId) {

      Country country = countryMapper.toEntity(countryService.findById(countryId));
      State state = stateMapper.toEntity(stateService.findByIdAndCountryId(stateId, countryId));
      City city = cityMapper.toEntity(cityService.findByIdAndStateId(cityId, stateId));

      state.setCountry(country);
      city.setState(state);
      return city;
   }
}
