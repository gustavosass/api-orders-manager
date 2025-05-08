package com.gustavosass.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gustavosass.orders.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByName(String name);
}