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
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 15, scale = 4)
    private BigDecimal initial_quantity;

    @Column(precision = 15, scale = 4)
    private BigDecimal current_quantity;

    @Column(precision = 15, scale = 4)
    private BigDecimal cost_price;

    private Date entry_date;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
}
