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
    private BigDecimal initialQuantity;
    private BigDecimal currentQuantity;
    private BigDecimal costPrice;
    private Date entryDate;
    private ItemDTO itemDTO;
}
