package com.gustavosass.orders.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gustavosass.orders.model.Client;
import com.gustavosass.orders.dto.ClientCreateDTO;
import com.gustavosass.orders.dto.ClientDTO;
import com.gustavosass.orders.dto.ClientUpdateDTO;

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

    public Client toEntity(ClientCreateDTO clientCreateDTO) {
        return Client.builder()
                .id(clientCreateDTO.getId())
                .name(clientCreateDTO.getName())
                .email(clientCreateDTO.getEmail())
                .birthDate(clientCreateDTO.getBirthDate())
                .phone(clientCreateDTO.getPhone())
                .document(clientCreateDTO.getDocument())
                .address(addressMapper.toEntity(clientCreateDTO.getAddressCreateDTO()))
                .build();
    }

    public Client toEntity(ClientUpdateDTO clientUpdateDTO) {
        return Client.builder()
                .id(clientUpdateDTO.getId())
                .name(clientUpdateDTO.getName())
                .email(clientUpdateDTO.getEmail())
                .birthDate(clientUpdateDTO.getBirthDate())
                .phone(clientUpdateDTO.getPhone())
                .document(clientUpdateDTO.getDocument())
                .address(addressMapper.toEntity(clientUpdateDTO.getAddressUpdateDTO()))
                .build();
    }
}