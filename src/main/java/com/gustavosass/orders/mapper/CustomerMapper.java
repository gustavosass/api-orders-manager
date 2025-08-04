package com.gustavosass.orders.mapper;

import com.gustavosass.orders.dto.AddressDTO;
import com.gustavosass.orders.dto.CustomerCreateDTO;
import com.gustavosass.orders.dto.CustomerDTO;
import com.gustavosass.orders.dto.CustomerUpdateDTO;
import com.gustavosass.orders.model.Address;
import com.gustavosass.orders.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    @Autowired
    private AddressMapper addressMapper;

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
                .address(customer.getAddress() == null ? null : addressMapper.toDTO(customer.getAddress()))
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
                .address(dto.getAddress() == null ? null : addressMapper.toEntity(dto.getAddress()))
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
                .address(dto.getAddress() == null ? null : addressMapper.toEntity(dto.getAddress()))
                .build();
    }
}
