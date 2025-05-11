package com.gustavosass.orders.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gustavosass.orders.model.address.Address;
import com.gustavosass.orders.model.city.City;
import com.gustavosass.orders.repository.AddressRepository;

@Service
public class AddressService {

   @Autowired
   private AddressRepository addressRepository;

   @Autowired
   private CityService cityService;

   public Address findById(Long id) {
      return addressRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Address not found"));
   }

   public List<Address> findAll() {
      return addressRepository.findAll();
   }

   public Address create(Address address) {

      if (address.getCity() != null) {
         //City city = cityService.create(address.getCity());
         //address.setCity(city);
      }

      return addressRepository.save(address);
      /*
      if (address.getPostalCode() != null){
         City city = cityService.create(address.getPostalCode());
         address.setCity(city);
         return addressRepository.save(address)*/
   }

   public Address update(Long id, Address address) {
      Address addressDb = addressRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Address not found"));
      address.setId(addressDb.getId());
      return addressRepository.save(address);
   }

   public void delete(Long id) {
      Address address = addressRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Address not found"));
      addressRepository.delete(address);
   }

}
