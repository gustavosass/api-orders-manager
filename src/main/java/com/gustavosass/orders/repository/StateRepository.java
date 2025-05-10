package com.gustavosass.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gustavosass.orders.model.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    State findByName(String name);

    @Query("SELECT s FROM State s WHERE s.name = :stateName AND s.country.name = :countryName")
    State findByNameAndCountryName(String stateName, String countryName);


}