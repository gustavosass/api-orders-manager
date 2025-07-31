package com.gustavosass.orders.mapper;

import com.gustavosass.orders.dto.BudgetCreateDTO;
import com.gustavosass.orders.dto.BudgetDTO;
import com.gustavosass.orders.dto.BudgetUpdateDTO;
import com.gustavosass.orders.enums.StatusBudgetEnum;
import com.gustavosass.orders.model.Budget;
import com.gustavosass.orders.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class BudgetMapper {

    @Autowired
    private CustomerMapper customerMapper;

    public Budget toEntity(BudgetCreateDTO dto){
        if (dto == null) return null;
        return Budget.builder()
                .customer(Customer.builder()
                        .id(dto.getCustomerId())
                        .build()
                )
                .build();
    }

    public Budget toEntity(BudgetUpdateDTO dto){
        if (dto == null) return null;
        return Budget.builder()
                .customer(Customer.builder()
                        .id(dto.getCustomerId())
                        .build()
                )
                .build();
    }

    public BudgetDTO toDTO(Budget entity){
        if (entity == null) return null;
        return BudgetDTO.builder()
                .id(entity.getId())
                .number(entity.getNumber())
                .budgetDate(entity.getBudgetDate())
                .customer(customerMapper.toDTO(entity.getCustomer()))
                .totalValue(entity.getTotalValue())
                .status(entity.getStatus())
                .approvedDate(entity.getApprovedDate())
                .cancelledDate(entity.getCancelledDate())
                .build();
    }

}
