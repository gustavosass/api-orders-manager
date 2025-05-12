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

		validateClient(clientDTO);

		return clientMapper.toDTO(
				clientRepository.save(client));
	}

	public ClientDTO update(Long id, ClientDTO clientDTO) {

		validateClient(clientDTO);

		findById(id);

		Client client = clientMapper.toEntity(clientDTO);
		client.setId(id);
		
		return clientMapper.toDTO(
				clientRepository.save(client));
	}

	public void delete(Long id) {
		findById(id);
		clientRepository.deleteById(id);
	}

	private void validateClient(ClientDTO clientDTO) {
		findByEmail(clientDTO.getEmail());
		findByDocument(clientDTO.getDocument());
	}

}