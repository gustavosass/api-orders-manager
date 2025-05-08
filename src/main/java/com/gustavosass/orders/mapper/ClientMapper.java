package com.gustavosass.orders.mapper;

import org.springframework.stereotype.Component;

import com.gustavosass.orders.dto.ClientDTO;
import com.gustavosass.orders.model.Client;

@Component
public class ClientMapper {
    
    public Client toEntity(ClientDTO clientDTO) {
        return Client.builder()
                .id(clientDTO.getId())
                .name(clientDTO.getName())
                .email(clientDTO.getEmail())
                .birthDate(clientDTO.getBirthDate())
                .phone(clientDTO.getPhone())
                .document(clientDTO.getDocument())
                .city(clientDTO.getCity())
                .street(clientDTO.getStreet())
                .number(clientDTO.getNumber())
                .district(clientDTO.getDistrict())
                .complement(clientDTO.getComplement())
                .postalCode(clientDTO.getPostalCode())
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
                .city(client.getCity())
                .street(client.getStreet())
                .number(client.getNumber())
                .district(client.getDistrict())
                .complement(client.getComplement())
                .postalCode(client.getPostalCode())
                .build();
    }
}