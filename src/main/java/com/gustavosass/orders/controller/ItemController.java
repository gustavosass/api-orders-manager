package com.gustavosass.orders.controller;

import com.gustavosass.orders.dto.*;
import com.gustavosass.orders.mapper.ItemMapper;
import com.gustavosass.orders.model.Item;
import com.gustavosass.orders.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/item")
@Tag(name = "Itens", description = "Cadastro de itens")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemMapper itemMapper;
    
    @GetMapping
    public ResponseEntity<List<ItemDTO>> findAll() {
        List<Item> items = itemService.findAll();
        return ResponseEntity.ok(items.stream().map(itemMapper::toDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> findById(@PathVariable Long id) {
        Item item = itemService.findById(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(itemMapper.toDto(item));
    }

    @PostMapping
    public ResponseEntity<ItemDTO> create(@RequestBody @Valid ItemCreateDTO dto) {
        Item item = itemMapper.toEntity(dto);
        item = itemService.create(item);
        return ResponseEntity.ok(itemMapper.toDto(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> update(@PathVariable Long id, @RequestBody @Valid ItemUpdateDTO dto) {
        Item item = itemMapper.toEntity(dto);
        item = itemService.update(id, item);
        return ResponseEntity.ok(itemMapper.toDto(item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
