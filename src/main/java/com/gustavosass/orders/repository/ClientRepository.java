package com.gustavosass.orders.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gustavosass.orders.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);
    Optional<Client> findByDocument(String document);
}