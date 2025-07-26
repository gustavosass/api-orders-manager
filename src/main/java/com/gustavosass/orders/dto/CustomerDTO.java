package com.gustavosass.orders.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class CustomerDTO {
    private Long id;
    private String name;
    private String email;
    private Date birthDate;
    private String phone;
    private String document;
    private AddressDTO address;
}
