package com.gustavosass.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gustavosass.orders.model.address.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}