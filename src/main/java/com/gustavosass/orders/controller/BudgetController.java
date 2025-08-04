package com.gustavosass.orders.controller;

import com.gustavosass.orders.dto.*;
import com.gustavosass.orders.mapper.BudgetMapper;
import com.gustavosass.orders.model.Budget;
import com.gustavosass.orders.service.BudgetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/budget")
@Tag(name = "Orçamento", description = "Cadastro de orçamento")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private BudgetMapper budgetMapper;

    @GetMapping
    public ResponseEntity<List<BudgetDTO>> findAll() {
        List<BudgetDTO> stocks = budgetService.findAll().stream().map(budgetMapper::toDTO).toList();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetDTO> findById(@PathVariable Long id) {
        Budget budget = budgetService.findById(id);
        return ResponseEntity.ok(budgetMapper.toDTO(budget));
    }

    @PostMapping
    public ResponseEntity<BudgetDTO> create(@RequestBody @Valid BudgetCreateDTO dto) {
        Budget budget = budgetMapper.toEntity(dto);
        budget = budgetService.create(budget);
        return ResponseEntity.ok(budgetMapper.toDTO(budget));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetDTO> update(@PathVariable Long id, @RequestBody @Valid BudgetUpdateDTO dto) {
        Budget budget = budgetMapper.toEntity(dto);
        budget = budgetService.update(id, budget);
        return ResponseEntity.ok(budgetMapper.toDTO(budget));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        findById(id);
        budgetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
