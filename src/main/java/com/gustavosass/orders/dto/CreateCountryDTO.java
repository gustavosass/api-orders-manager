package com.gustavosass.orders.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCountryDTO {
   @NotNull(message = "Name cannot be null")
    private String name;
}
