package com.gustavosass.orders.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gustavosass.orders.model.Client;
import com.gustavosass.orders.repository.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressService addressService;

    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Client not found"));
    }

    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Client not found"));
    }

    public Client findByDocument(String document) {
        return clientRepository.findByDocument(document)
                .orElseThrow(() -> new NoSuchElementException("Client not found"));
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client create(Client client) {
        validateClient(client);
        if (clientRepository.findByEmail(client.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
        if (clientRepository.findByDocument(client.getDocument()).isPresent()) {
            throw new IllegalArgumentException("Document already registered");
        }

        if (client.getAddress() != null) {
            addressService.create(client.getAddress());
        }

        return clientRepository.save(client);
    }

    public Client update(Long id, Client client) {
        validateClient(client);
        Client clientDb = clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Client not found"));
        
        if (!clientDb.getEmail().equals(client.getEmail()) && 
            clientRepository.findByEmail(client.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
        
        if (!clientDb.getDocument().equals(client.getDocument()) && 
            clientRepository.findByDocument(client.getDocument()).isPresent()) {
            throw new IllegalArgumentException("Document already registered");
        }

        client.setId(id);
        return clientRepository.save(client);
    }

    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new NoSuchElementException("Client not found");
        }
        clientRepository.deleteById(id);
    }

    private void validateClient(Client client) {
        if (client.getEmail() == null || client.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (client.getDocument() == null || client.getDocument().isEmpty()) {
            throw new IllegalArgumentException("Document is required");
        }
        if (client.getName() == null || client.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
    }
}