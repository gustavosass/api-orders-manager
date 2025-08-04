package com.gustavosass.orders.mapper;

import com.gustavosass.orders.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gustavosass.orders.model.Address;
import com.gustavosass.orders.dto.AddressCreateDTO;
import com.gustavosass.orders.dto.AddressDTO;
import com.gustavosass.orders.dto.AddressUpdateDTO;

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
                .city(address.getCity() == null ? null : cityMapper.toDTO(address.getCity()))
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
                .city(addressDTO.getCity() == null ? null : cityMapper.toEntity(addressDTO.getCity()))
                .build();
    }

    public Address toEntity(AddressCreateDTO addressCreateDTO) {
        return Address.builder()
                .street(addressCreateDTO.getStreet())
                .number(addressCreateDTO.getNumber())
                .district(addressCreateDTO.getDistrict())
                .complement(addressCreateDTO.getComplement())
                .postalCode(addressCreateDTO.getPostalCode())
                .city(City.builder()
                        .id(addressCreateDTO.getCityId())
                        .build())
                .build();
    }

    public Address toEntity(AddressUpdateDTO addressUpdateDTO) {
        return Address.builder()
                .street(addressUpdateDTO.getStreet())
                .number(addressUpdateDTO.getNumber())
                .district(addressUpdateDTO.getDistrict())
                .complement(addressUpdateDTO.getComplement())
                .postalCode(addressUpdateDTO.getPostalCode())
                .city(City.builder()
                        .id(addressUpdateDTO.getCityId())
                        .build())
                .build();
    }
    

}
