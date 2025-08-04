package com.gustavosass.orders.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class CustomerCreateDTO {
    private String name;
    private String email;
    private Date birthDate;
    private String phone;
    private String document;
    private AddressDTO address;
}
