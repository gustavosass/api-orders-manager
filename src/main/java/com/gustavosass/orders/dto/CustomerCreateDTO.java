package com.gustavosass.orders.dto;

import lombok.Data;
import java.util.Date;

@Data
public class CustomerCreateDTO {
    private String name;
    private String email;
    private Date birthDate;
    private String phone;
    private String document;
    private AddressDTO address;
}
