package com.gustavosass.orders.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gustavosass.orders.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findByStateId(Long stateId);

    Optional<City> findByNameAndStateId(String name, Long id);

    boolean existsByName(String name);

    Optional<City> findByIdAndStateId(Long id, Long stateId);
    
}