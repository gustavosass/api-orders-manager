package com.gustavosass.orders.mapper;

import org.springframework.stereotype.Component;

import com.gustavosass.orders.dto.RegisterDTO;
import com.gustavosass.orders.dto.UserDTO;
import com.gustavosass.orders.model.User;

@Component
public class UserMapper {

    public User toEntity(RegisterDTO registerDTO) {
        return User.builder()
                .name(registerDTO.getName())
                .email(registerDTO.getEmail())
                .password(registerDTO.getPassword())
                .role(registerDTO.getRole())
                .build();
    }

    public User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .role(userDTO.getRole())
                .build();
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    public RegisterDTO toRegisterDTO(User user) {
        return new RegisterDTO(user.getName(), user.getEmail(), user.getPassword(), user.getRole());
    }
}