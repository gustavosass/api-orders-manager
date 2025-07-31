package com.gustavosass.orders.dto;

import com.gustavosass.orders.enums.StatusBudgetEnum;
import com.gustavosass.orders.model.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
public class BudgetCreateDTO {

    @NotNull
    private Long customerId;
}
