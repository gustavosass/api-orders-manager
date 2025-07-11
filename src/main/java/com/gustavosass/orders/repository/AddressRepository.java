package com.gustavosass.orders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gustavosass.orders.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
   List<Address> findAllByClientId(Long clientId);
}