package com.gustavosass.orders.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gustavosass.orders.model.Address;
import com.gustavosass.orders.repository.AddressRepository;

@Service
@Transactional(readOnly = true)
public class AddressService {

   @Autowired
   private AddressRepository addressRepository;

   public Address findById(Long id) {
      return addressRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Address not found"));
   }

   public List<Address> findAll() {
      return addressRepository.findAll();
   }

   @Transactional
   public Address create(Address address) {
      return addressRepository.save(address);
   }

   @Transactional
   public Address update(Long id, Address address) {
      findById(id);
      address.setId(id);
      return addressRepository.save(address);
   }

   @Transactional
   public void delete(Long id) {
      findById(id);
      addressRepository.deleteById(id);
   }
}
