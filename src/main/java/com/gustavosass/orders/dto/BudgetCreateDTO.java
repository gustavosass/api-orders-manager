package com.gustavosass.orders.dto;
import jakarta.validation.constraints.NotNull;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BudgetCreateDTO {

    @NotNull
    private Long customerId;
}
