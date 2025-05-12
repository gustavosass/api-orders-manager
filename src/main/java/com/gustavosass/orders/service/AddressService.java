package com.gustavosass.orders.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gustavosass.orders.integration.viacep.ViaCepClient;
import com.gustavosass.orders.mapper.AddressMapper;
import com.gustavosass.orders.model.address.Address;
import com.gustavosass.orders.model.address.dto.AddressCreateDTO;
import com.gustavosass.orders.model.address.dto.AddressDTO;
import com.gustavosass.orders.model.address.dto.AddressUpdateDTO;
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

   public AddressDTO findById(Long id) {
      return addressMapper.toDTO(addressRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Address not found")));
   }

   public List<Address> findAll() {
      return addressRepository.findAll();
   }

   public AddressDTO create(AddressCreateDTO addressCreateDTO) {

      validateHierarchy(addressCreateDTO.getCountryId(), addressCreateDTO.getStateId(), addressCreateDTO.getCityId());

      Address address = addressMapper.toEntity(addressCreateDTO);

      return addressMapper.toDTO(
            addressRepository.save(address));
   }

   public AddressDTO update(Long id, AddressUpdateDTO addressUpdateDTO) {
      findById(id);

      validateHierarchy(addressUpdateDTO.getCountryId(), addressUpdateDTO.getStateId(), addressUpdateDTO.getCityId());

      Address address = addressMapper.toEntity(addressUpdateDTO);
      address.setId(id);
      return addressMapper.toDTO(addressRepository.save(address));
   }

   public void delete(Long id) {
      findById(id);
      addressRepository.deleteById(id);
   }

   private void validateHierarchy(Long countryId, Long stateId, Long cityId) {
      countryService.findById(countryId);
      stateService.findByIdAndCountryId(stateId, countryId);
      cityService.findByIdAndStateId(cityId, stateId);
   }
}
