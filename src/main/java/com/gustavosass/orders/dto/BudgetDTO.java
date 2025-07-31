package com.gustavosass.orders.dto;

import com.gustavosass.orders.enums.StatusBudgetEnum;
import com.gustavosass.orders.model.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
public class BudgetDTO {

    private Long id;
    private String number;
    private Date budgetDate;
    private CustomerDTO customer;
    private BigDecimal totalValue;
    private StatusBudgetEnum status;
    private Date approvedDate;
    private Date cancelledDate;
}
