package com.gustavosass.orders.dto;

import com.gustavosass.orders.enums.RoleEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private RoleEnum role;
}
