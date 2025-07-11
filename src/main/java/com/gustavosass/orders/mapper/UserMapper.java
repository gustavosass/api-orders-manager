package com.gustavosass.orders.mapper;

import org.springframework.stereotype.Component;

import com.gustavosass.orders.model.User;
import com.gustavosass.orders.dto.UserRegisterDTO;
import com.gustavosass.orders.dto.UserDTO;

@Component
public class UserMapper {

    public User toEntity(UserRegisterDTO userRegisterDTO) {
        return User.builder()
                .name(userRegisterDTO.getName())
                .email(userRegisterDTO.getEmail())
                .password(userRegisterDTO.getPassword())
                .role(userRegisterDTO.getRole())
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

    public UserRegisterDTO toRegisterDTO(User user) {
        return new UserRegisterDTO(user.getName(), user.getEmail(), user.getPassword(), user.getRole());
    }
}