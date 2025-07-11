package com.gustavosass.orders.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gustavosass.orders.model.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    State findByName(String name);

    @Query("SELECT s FROM State s WHERE s.name = :stateName AND s.country.id = :id")
    Optional<State> findByNameAndCountryId(String stateName, Long id);

    boolean existsByName(String name);

    Optional<State> findByIdAndCountryId(Long id, Long countryId);

}