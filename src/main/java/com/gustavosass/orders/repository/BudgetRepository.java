package com.gustavosass.orders.repository;

import com.gustavosass.orders.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    @Query("SELECT MAX(b.id) FROM Budget b")
    Integer findMaxId();
}
