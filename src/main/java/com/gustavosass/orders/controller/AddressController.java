package com.gustavosass.orders.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gustavosass.orders.dto.AddressCreateDTO;
import com.gustavosass.orders.dto.AddressDTO;
import com.gustavosass.orders.dto.AddressUpdateDTO;
import com.gustavosass.orders.mapper.AddressMapper;
import com.gustavosass.orders.model.Address;
import com.gustavosass.orders.service.CustomerAddressService;

import jakarta.validation.Valid;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/{customerId}/address")
@Tag(name = "Endereços", description = "Operações relacionadas aos endereços")
public class AddressController {

    @Autowired
    private CustomerAddressService customerAddressService;

    @Autowired
    private AddressMapper addressMapper;

    @GetMapping
    public ResponseEntity<AddressDTO> getAddress(@PathVariable Long customerId) {
        Address address = customerAddressService.getCustomerAddress(customerId);
        if (address == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(addressMapper.toDTO(address));
    }

    @PostMapping
    public ResponseEntity<AddressDTO> create(@PathVariable Long customerId,
                                             @RequestBody @Valid AddressCreateDTO addressCreateDTO) {
        Address address = addressMapper.toEntity(addressCreateDTO);
        Address created = customerAddressService.addAddressToCustomer(customerId, address);
        if (created == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(201).body(addressMapper.toDTO(created));
    }

    @PutMapping
    public ResponseEntity<AddressDTO> update(@PathVariable Long customerId,
                                             @RequestBody @Valid AddressUpdateDTO addressUpdateDTO) {
        Address address = addressMapper.toEntity(addressUpdateDTO);
        Address updated = customerAddressService.updateCustomerAddress(customerId, address);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(addressMapper.toDTO(updated));
    }

    @DeleteMapping
    public void delete(@PathVariable Long customerId) {
        Address address = customerAddressService.getCustomerAddress(customerId);
        if (address == null) {
            throw new NoSuchElementException("Unregistered address");
        }
        customerAddressService.removeCustomerAddress(customerId);
    }
}

