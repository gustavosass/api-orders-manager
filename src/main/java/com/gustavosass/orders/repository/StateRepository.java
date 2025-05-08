package com.gustavosass.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gustavosass.orders.model.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    State findByName(String name);
}