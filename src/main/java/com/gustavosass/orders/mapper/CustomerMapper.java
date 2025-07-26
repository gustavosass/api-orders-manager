package com.gustavosass.orders.mapper;

import com.gustavosass.orders.dto.AddressDTO;
import com.gustavosass.orders.dto.CustomerCreateDTO;
import com.gustavosass.orders.dto.CustomerDTO;
import com.gustavosass.orders.dto.CustomerUpdateDTO;
import com.gustavosass.orders.model.Address;
import com.gustavosass.orders.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerDTO toDTO(Customer customer) {
        if (customer == null)
            return null;
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .birthDate(customer.getBirthDate())
                .phone(customer.getPhone())
                .document(customer.getDocument())
                .address(addressToDTO(customer.getAddress()))
                .build();
    }

    public Customer toEntity(CustomerCreateDTO dto) {
        if (dto == null)
            return null;
        return Customer.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .birthDate(dto.getBirthDate())
                .phone(dto.getPhone())
                .document(dto.getDocument())
                .address(dtoToAddress(dto.getAddress()))
                .build();
    }

    public Customer toEntity(CustomerUpdateDTO dto) {
        if (dto == null)
            return null;
        return Customer.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .birthDate(dto.getBirthDate())
                .phone(dto.getPhone())
                .document(dto.getDocument())
                .address(dtoToAddress(dto.getAddress()))
                .build();
    }

    public AddressDTO addressToDTO(Address address) {
        if (address == null)
            return null;
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

    public Address dtoToAddress(AddressDTO dto) {
        if (dto == null)
            return null;
        return Address.builder()
                .id(dto.getId())
                .street(dto.getStreet())
                .number(dto.getNumber())
                .district(dto.getDistrict())
                .complement(dto.getComplement())
                .postalCode(dto.getPostalCode())
                .cityId(dto.getCityId())
                .build();
    }
}
