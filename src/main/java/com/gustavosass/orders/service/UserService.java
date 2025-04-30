package com.gustavosass.orders.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gustavosass.orders.model.User;
import com.gustavosass.orders.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User create(User user) {
        return userRepository.save(user);
    }
}
