package com.gustavosass.orders.dto;

import lombok.*;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class CreateStateDTO {

   @NotNull(message = "Name cannot be null")
    private String name;
    private String initials;
    @NotNull(message = "Country ID cannot be null")
    private Long idCountry;
}
