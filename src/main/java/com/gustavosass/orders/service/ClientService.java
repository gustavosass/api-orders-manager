package com.gustavosass.orders.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gustavosass.orders.mapper.ClientMapper;
import com.gustavosass.orders.model.client.Client;
import com.gustavosass.orders.model.client.dto.ClientDTO;
import com.gustavosass.orders.repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private AddressService addressService;

	@Autowired
	private ClientMapper clientMapper;

	public ClientDTO findById(Long id) {
		return clientMapper
				.toDTO(clientRepository.findById(id)
						.orElseThrow(() -> new NoSuchElementException("Client not found")));
	}

	public ClientDTO findByEmail(String email) {
		return clientMapper
				.toDTO(clientRepository.findByEmail(email)
				.orElseThrow(() -> new NoSuchElementException("Client not found")));
	}

	public ClientDTO findByDocument(String document) {
		return clientMapper
				.toDTO(clientRepository.findByDocument(document)
				.orElseThrow(() -> new NoSuchElementException("Client not found")));
	}

	public List<ClientDTO> findAll() {
		return clientRepository.findAll().stream().map(clientMapper::toDTO).toList();
	}

	public ClientDTO create(ClientDTO clientDTO) {

		Client client = clientMapper.toEntity(clientDTO);	

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

		return clientMapper.toDTO(clientRepository.save(client));
	}

	public ClientDTO update(Long id, ClientDTO clientDTO) {
		
		Client client = clientMapper.toEntity(clientDTO);
		
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
		return clientMapper.toDTO(clientRepository.save(client));
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