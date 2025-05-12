package com.gustavosass.orders.controller;

import java.util.List;

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

import com.gustavosass.orders.model.address.dto.AddressCreateDTO;
import com.gustavosass.orders.model.address.dto.AddressDTO;
import com.gustavosass.orders.model.address.dto.AddressUpdateDTO;
import com.gustavosass.orders.service.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<AddressDTO>> findAll() {
        return ResponseEntity.ok(addressService.findAll());
    }

    @PostMapping
    public ResponseEntity<AddressDTO> create(@org.springframework.web.bind.annotation.RequestBody @Valid AddressCreateDTO addressCreateDTO) {
        return ResponseEntity.ok(addressService.create(addressCreateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> update(@PathVariable Long id, @RequestBody @Valid AddressUpdateDTO addressUpdateDTO) {
        return ResponseEntity.ok(addressService.update(id, addressUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }    

}
