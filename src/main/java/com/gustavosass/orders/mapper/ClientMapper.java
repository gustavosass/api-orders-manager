package com.gustavosass.orders.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gustavosass.orders.model.client.Client;
import com.gustavosass.orders.model.client.dto.ClientDTO;

@Component
public class ClientMapper {

    @Autowired
    private AddressMapper addressMapper;
      
    public Client toEntity(ClientDTO clientDTO) {
        return Client.builder()
                .id(clientDTO.getId())
                .name(clientDTO.getName())
                .email(clientDTO.getEmail())
                .birthDate(clientDTO.getBirthDate())
                .phone(clientDTO.getPhone())
                .document(clientDTO.getDocument())
                .address(
                    addressMapper.toEntity(clientDTO.getAddressDTO())
                )
                .build();
    }

    public ClientDTO toDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .birthDate(client.getBirthDate())
                .phone(client.getPhone())
                .document(client.getDocument())
                .addressDTO(addressMapper.toDTO(client.getAddress()))
                .build();
    }
}