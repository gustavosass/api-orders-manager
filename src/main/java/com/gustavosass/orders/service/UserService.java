package com.gustavosass.orders.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gustavosass.orders.model.user.User;
import com.gustavosass.orders.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;


    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User create(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    
    public User update(Long id, User user) {
        User userDb = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));

        user.setId(id);
        user.setPassword(userDb.getPassword());
        
        return userRepository.save(user);
    }

    public void updatePassword(Long id, String password) {
        User userDb = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
        
        userDb.setPassword(passwordEncoder.encode(password));
        
        userRepository.save(userDb);
    }

    
    public void delete(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }
}
