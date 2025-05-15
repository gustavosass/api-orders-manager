package com.gustavosass.orders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gustavosass.orders.model.address.dto.AddressDTO;
import com.gustavosass.orders.model.address.dto.AddressUpdateDTO;
import com.gustavosass.orders.service.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/clients/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> update(@PathVariable Long clientId, @PathVariable Long id, @RequestBody @Valid AddressUpdateDTO addressUpdateDTO) {
        return ResponseEntity.ok(addressService.update(clientId, id, addressUpdateDTO));
    }

}
