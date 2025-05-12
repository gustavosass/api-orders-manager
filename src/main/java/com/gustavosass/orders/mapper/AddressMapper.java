package com.gustavosass.orders.mapper;

import org.springframework.stereotype.Component;

import com.gustavosass.orders.model.address.Address;
import com.gustavosass.orders.model.address.dto.AddressCreateDTO;
import com.gustavosass.orders.model.address.dto.AddressDTO;
import com.gustavosass.orders.model.address.dto.AddressUpdateDTO;

@Component
public class AddressMapper {

    public AddressDTO toDTO(Address address) {
        return AddressDTO.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .district(address.getDistrict())
                .complement(address.getComplement())
                .postalCode(address.getPostalCode())
                .build();
    }

    public Address toEntity(AddressDTO addressDTO) {

        return Address.builder()
                .street(addressDTO.getStreet())
                .number(addressDTO.getNumber())
                .district(addressDTO.getDistrict())
                .complement(addressDTO.getComplement())
                .postalCode(addressDTO.getPostalCode())
                .build();
    }

    public Address toEntity(AddressCreateDTO addressCreateDTO) {
        return Address.builder()
                .street(addressCreateDTO.getStreet())
                .number(addressCreateDTO.getNumber())
                .district(addressCreateDTO.getDistrict())
                .complement(addressCreateDTO.getComplement())
                .postalCode(addressCreateDTO.getPostalCode())
                .build();
    }

    public Address toEntity(AddressUpdateDTO addressUpdateDTO) {
        return Address.builder()
                .street(addressUpdateDTO.getStreet())
                .number(addressUpdateDTO.getNumber())
                .district(addressUpdateDTO.getDistrict())
                .complement(addressUpdateDTO.getComplement())
                .postalCode(addressUpdateDTO.getPostalCode())
                .build();
    }
    

}
