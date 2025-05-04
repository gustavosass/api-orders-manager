package com.gustavosass.orders.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gustavosass.orders.model.User;
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
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Id not found"));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User create(User user) {
        return userRepository.save(user);
    }
    
    public User update(User user) {
        User userDb = userRepository.findById(user.getId()).orElseThrow(() -> new NoSuchElementException("User not found with id"));
        
        user.setPassword(userDb.getPassword());
        
        return userRepository.save(user);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public void updatePassword(Long id, String password) {
        User userDb = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found with id"));
        
        userDb.setPassword(passwordEncoder.encode(password));
        
        userRepository.save(userDb);
    }
}
