package com.gustavosass.orders.model;

import com.gustavosass.orders.enums.StatusBudgetEnum;
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
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    private Date budgetDate;

    private Customer customer;

    @Column(precision = 15, scale = 4)
    private BigDecimal totalValue;

    @Enumerated(EnumType.STRING)
    private StatusBudgetEnum status;

    private Date approvedDate;
    private Date cancelledDate;






}
