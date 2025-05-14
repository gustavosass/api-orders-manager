package com.gustavosass.orders.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.gustavosass.orders.model.address.Address;
import com.gustavosass.orders.model.address.dto.AddressCreateDTO;
import com.gustavosass.orders.model.address.dto.AddressDTO;
import com.gustavosass.orders.model.address.dto.AddressUpdateDTO;

@Component
public class AddressMapper {

    @Autowired
    private CityMapper cityMapper;



    public AddressDTO toDTO(Address address) {
        return AddressDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .number(address.getNumber())
                .district(address.getDistrict())
                .complement(address.getComplement())
                .postalCode(address.getPostalCode())
                .cityDTO(cityMapper.toDTO(address.getCity()))
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
                .city(cityMapper.toEntity(addressDTO.getCityDTO()))
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
                .id(addressUpdateDTO.getId())
                .street(addressUpdateDTO.getStreet())
                .number(addressUpdateDTO.getNumber())
                .district(addressUpdateDTO.getDistrict())
                .complement(addressUpdateDTO.getComplement())
                .postalCode(addressUpdateDTO.getPostalCode())
                .build();
    }
    

}
