package com.gustavosass.orders.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "initial_quantity", precision = 15, scale = 4)
    private BigDecimal initialQuantity;

    @Column(name = "current_quantity", precision = 15, scale = 4)
    private BigDecimal currentQuantity;

    @Column(name = "cost_price", precision = 15, scale = 4)
    private BigDecimal costPrice;

    @Column(name = "entry_date")
    private Date entryDate;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
}
