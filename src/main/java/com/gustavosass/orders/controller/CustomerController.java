package com.gustavosass.orders.controller;

import com.gustavosass.orders.dto.CustomerCreateDTO;
import com.gustavosass.orders.dto.CustomerDTO;
import com.gustavosass.orders.dto.CustomerUpdateDTO;
import com.gustavosass.orders.mapper.CustomerMapper;
import com.gustavosass.orders.model.Customer;
import com.gustavosass.orders.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Clientes", description = "Operações relacionadas aos clientes")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMapper customerMapper;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> findAll() {
        List<Customer> customers = customerService.findAll();
        List<CustomerDTO> dtos = customers.stream().map(customerMapper::toDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customerMapper.toDTO(customer));
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> create(@RequestBody @Valid CustomerCreateDTO customerCreateDTO) {
        Customer customer = customerMapper.toEntity(customerCreateDTO);
        customer = customerService.create(customer);
        return ResponseEntity.ok(customerMapper.toDTO(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> update(@PathVariable Long id, @RequestBody @Valid CustomerUpdateDTO customerUpdateDTO) {
        Customer customer = customerMapper.toEntity(customerUpdateDTO);
        customer = customerService.update(id, customer);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customerMapper.toDTO(customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
