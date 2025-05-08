package com.gustavosass.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gustavosass.orders.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("SELECT c FROM City c WHERE c.name = :name AND c.state = :state")
    City findByNameAndState(String name, String state);
    
}