package com.gustavosass.orders.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemUpdateDTO {

    @NotNull
    String name;
    String description;
}
