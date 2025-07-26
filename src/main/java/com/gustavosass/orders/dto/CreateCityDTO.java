package com.gustavosass.orders.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCityDTO {

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "State ID cannot be null")
    private Long idState;
}
