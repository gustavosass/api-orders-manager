package com.gustavosass.orders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gustavosass.orders.mapper.UserMapper;
import com.gustavosass.orders.model.user.User;
import com.gustavosass.orders.model.user.dto.PasswordDTO;
import com.gustavosass.orders.model.user.dto.RegisterDTO;
import com.gustavosass.orders.model.user.dto.UserDTO;
import com.gustavosass.orders.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(userMapper.toDTO(user));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<User> users = userService.findAll();
        List<UserDTO> usersDTO = users.stream().map(userMapper::toDTO).toList();
        return ResponseEntity.ok(usersDTO);
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody RegisterDTO registerDTO) {
        User user = userMapper.toEntity(registerDTO);
        user = userService.create(user);
        return ResponseEntity.ok(userMapper.toDTO(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user = userService.update(id, user);
        return ResponseEntity.ok(userMapper.toDTO(user));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody PasswordDTO passwordDTO) {
        userService.updatePassword(id, passwordDTO.getPassword());
        return ResponseEntity.noContent().build();
    }

}
