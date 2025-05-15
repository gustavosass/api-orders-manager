package com.gustavosass.orders.service;

import org.springframework.dao.DuplicateKeyException;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gustavosass.orders.mapper.AddressMapper;
import com.gustavosass.orders.mapper.ClientMapper;
import com.gustavosass.orders.model.address.Address;
import com.gustavosass.orders.model.client.Client;
import com.gustavosass.orders.model.client.dto.ClientCreateDTO;
import com.gustavosass.orders.model.client.dto.ClientDTO;
import com.gustavosass.orders.model.client.dto.ClientUpdateDTO;
import com.gustavosass.orders.repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private AddressService addressService;

	@Autowired
	private ClientMapper clientMapper;

	@Autowired
	private AddressMapper addressMapper;

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

	public ClientDTO create(ClientCreateDTO clientCreateDTO) {

		Client client = clientMapper.toEntity(clientCreateDTO);

		validateClient(client);

		Address address = addressMapper.toEntity(addressService.create(clientCreateDTO.getAddressCreateDTO()));
		client.setAddress(address);

		return clientMapper.toDTO(
				clientRepository.save(client));
	}

	public ClientDTO update(Long id, ClientUpdateDTO clientUpdateDTO) {

		findById(id);

		Client client = clientMapper.toEntity(clientUpdateDTO);
		client.setId(id);

		validateClient(client);

		Address address = addressMapper.toEntity(addressService.update(client.getId(), clientUpdateDTO.getAddressUpdateDTO()));
		client.setAddress(address);

		return clientMapper.toDTO(
				clientRepository.save(client));
	}

	public void delete(Long id) {
		findById(id);
		clientRepository.deleteById(id);
	}

	private void validateClient(Client client) {
		if (clientRepository.findByEmail(client.getEmail()).isPresent()) {
			throw new DuplicateKeyException("Email already exists");
		}
		if (clientRepository.findByDocument(client.getDocument()).isPresent()) {
			throw new DuplicateKeyException("Document already exists");
		}

	}

}