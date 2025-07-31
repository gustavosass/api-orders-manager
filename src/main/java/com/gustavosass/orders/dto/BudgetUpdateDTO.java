package com.gustavosass.orders.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BudgetUpdateDTO {

    @NotNull
    private Long customerId;
}
