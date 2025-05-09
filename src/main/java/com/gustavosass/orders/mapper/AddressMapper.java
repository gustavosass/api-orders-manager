package com.gustavosass.orders.mapper;

import org.springframework.stereotype.Component;

import com.gustavosass.orders.dto.AddressDTO;
import com.gustavosass.orders.model.Address;

@Component
public class AddressMapper {

    public Address toEntity(AddressDTO addressDTO) {
        return Address.builder()
                .street(addressDTO.getStreet())
                .number(addressDTO.getNumber())
                .district(addressDTO.getDistrict())
                .complement(addressDTO.getComplement())
                .postalCode(addressDTO.getPostalCode())
                .city(addressDTO.getCity())
                .build();
    }

    public AddressDTO toDTO(Address address) {
        return AddressDTO.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .district(address.getDistrict())
                .complement(address.getComplement())
                .postalCode(address.getPostalCode())
                .city(address.getCity())
                .build();
    }
}
