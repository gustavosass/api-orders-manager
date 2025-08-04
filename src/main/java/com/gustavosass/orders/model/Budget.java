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
@Table(name = "budget")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    @Column(name = "budget_date")
    private Date budgetDate;

    @Column(name = "total_value", precision = 15, scale = 4)
    private BigDecimal totalValue;

    @Enumerated(EnumType.STRING)
    private StatusBudgetEnum status;

    @Column(name = "approved_date")
    private Date approvedDate;

    @Column(name = "cancelled_date")
    private Date cancelledDate;

    @ManyToOne
    private Customer customer;







}
