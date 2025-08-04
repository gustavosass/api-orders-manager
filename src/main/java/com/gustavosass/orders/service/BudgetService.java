package com.gustavosass.orders.service;

import com.gustavosass.orders.enums.StatusBudgetEnum;
import com.gustavosass.orders.mapper.CustomerMapper;
import com.gustavosass.orders.model.Budget;
import com.gustavosass.orders.model.Customer;
import com.gustavosass.orders.model.Item;
import com.gustavosass.orders.model.Stock;
import com.gustavosass.orders.repository.BudgetRepository;
import com.gustavosass.orders.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private CustomerService customerService;


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
        Customer customer = customerService.findById(budget.getCustomer().getId());
        Integer number = budgetRepository.findMaxId();
        budget.setNumber(String.format("ORC%03d", number == null ? 1 : number));
        budget.setBudgetDate(new Date());
        budget.setStatus(StatusBudgetEnum.PEIDING);
        budget.setCustomer(customer);
        return budgetRepository.save(budget);
    }

    @Transactional
    public Budget update (Long id, Budget budget){
        Budget existingBudget = findById(id);

        if (! budget.getCustomer().getId().equals(existingBudget.getCustomer().getId())){
            Customer existingCustomer = customerService.findById(budget.getCustomer().getId());
            existingBudget.setCustomer(existingCustomer);
        }
        return budgetRepository.save(existingBudget);
    }

    @Transactional
    public void delete(Long id){
        findById(id);
        budgetRepository.deleteById(id);
    }
}
