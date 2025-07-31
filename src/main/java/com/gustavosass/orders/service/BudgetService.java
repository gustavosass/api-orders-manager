package com.gustavosass.orders.service;

import com.gustavosass.orders.model.Budget;
import com.gustavosass.orders.model.Item;
import com.gustavosass.orders.model.Stock;
import com.gustavosass.orders.repository.BudgetRepository;
import com.gustavosass.orders.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;


    @Transactional(readOnly = true)
    public Budget findById(Long id){
        return budgetRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Budget not found."));
    }

    @Transactional(readOnly = true)
    public List <Budget> findAll(){
        return budgetRepository.findAll();
    }

    @Transactional
    public Budget create (Budget budget){
        return budgetRepository.save(budget);
    }

    @Transactional
    public Budget update (Long id, Budget budget){
        Budget existingStock = findById(id);


        return budgetRepository.save(existingStock);
    }

    @Transactional
    public void delete(Long id){
        findById(id);
        budgetRepository.deleteById(id);
    }
}
