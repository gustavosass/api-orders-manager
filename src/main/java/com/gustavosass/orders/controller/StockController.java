package com.gustavosass.orders.controller;

import com.gustavosass.orders.dto.StockCreateDTO;
import com.gustavosass.orders.dto.StockUpdateDTO;
import com.gustavosass.orders.dto.StockDTO;
import com.gustavosass.orders.mapper.StockMapper;
import com.gustavosass.orders.model.Stock;
import com.gustavosass.orders.service.StockService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
@Tag(name = "Estoque", description = "Cadastro de estoque")
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockMapper stockMapper;

    @GetMapping
    public ResponseEntity<List<StockDTO>> findAll() {
        List<StockDTO> stocks = stockService.findAll().stream().map(stockMapper::toDTO).toList();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockDTO> findById(@PathVariable Long id) {
        Stock stock = stockService.findById(id);
        if (stock == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(stockMapper.toDTO(stock));
    }

    @PostMapping
    public ResponseEntity<StockDTO> create(@RequestBody @Valid StockCreateDTO dto) {
        Stock stock = stockMapper.toEntity(dto);
        stock = stockService.create(stock);
        return ResponseEntity.ok(stockMapper.toDTO(stock));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockDTO> update(@PathVariable Long id, @RequestBody @Valid StockUpdateDTO dto) {
        Stock stock = stockMapper.toEntity(dto);
        stock = stockService.update(id, stock);
        return ResponseEntity.ok(stockMapper.toDTO(stock));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        stockService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
