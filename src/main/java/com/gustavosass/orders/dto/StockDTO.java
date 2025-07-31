package com.gustavosass.orders.dto;

import com.gustavosass.orders.model.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
public class StockDTO {
    private Long id;
    private BigDecimal initial_quantity;
    private BigDecimal current_quantity;
    private BigDecimal cost_price;
    private Date entry_date;
    private ItemDTO itemDTO;
}
