package com.gustavosass.orders.mapper;

import org.springframework.stereotype.Component;

import com.gustavosass.orders.model.Address;
import com.gustavosass.orders.dto.AddressCreateDTO;
import com.gustavosass.orders.dto.AddressDTO;
import com.gustavosass.orders.dto.AddressUpdateDTO;

@Component
public class AddressMapper {

    public AddressDTO toDTO(Address address) {
        return AddressDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .number(address.getNumber())
                .district(address.getDistrict())
                .complement(address.getComplement())
                .postalCode(address.getPostalCode())
                .cityId(address.getCityId())
                .build();
    }

    public Address toEntity(AddressDTO addressDTO) {
        return Address.builder()
                .id(addressDTO.getId())
                .street(addressDTO.getStreet())
                .number(addressDTO.getNumber())
                .district(addressDTO.getDistrict())
                .complement(addressDTO.getComplement())
                .postalCode(addressDTO.getPostalCode())
                .cityId(addressDTO.getCityId())
                .build();
    }

    public Address toEntity(AddressCreateDTO addressCreateDTO) {
        return Address.builder()
                .street(addressCreateDTO.getStreet())
                .number(addressCreateDTO.getNumber())
                .district(addressCreateDTO.getDistrict())
                .complement(addressCreateDTO.getComplement())
                .postalCode(addressCreateDTO.getPostalCode())
                .cityId(addressCreateDTO.getCityId())
                .build();
    }

    public Address toEntity(AddressUpdateDTO addressUpdateDTO) {
        return Address.builder()
                .street(addressUpdateDTO.getStreet())
                .number(addressUpdateDTO.getNumber())
                .district(addressUpdateDTO.getDistrict())
                .complement(addressUpdateDTO.getComplement())
                .postalCode(addressUpdateDTO.getPostalCode())
                .cityId(addressUpdateDTO.getCityId())
                .build();
    }
    

}
