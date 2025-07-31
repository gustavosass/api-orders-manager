package com.gustavosass.orders.dto;

import com.gustavosass.orders.model.Item;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
public class StockCreateDTO {

    @NotNull
    private BigDecimal initial_quantity;

    @NotNull
    private BigDecimal current_quantity;

    @NotNull
    private BigDecimal cost_price;

    @NotNull
    private Date entry_date;

    @NotNull
    private Long itemId;
}