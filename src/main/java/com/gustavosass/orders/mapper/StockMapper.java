package com.gustavosass.orders.mapper;

import com.gustavosass.orders.dto.StockCreateDTO;
import com.gustavosass.orders.dto.StockDTO;
import com.gustavosass.orders.dto.StockUpdateDTO;
import com.gustavosass.orders.model.Item;
import com.gustavosass.orders.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {

    @Autowired
    private ItemMapper itemMapper;

    public StockDTO toDTO(Stock stock) {
        if (stock == null) return null;

        return StockDTO.builder()
                .id(stock.getId())
                .initial_quantity(stock.getInitial_quantity())
                .current_quantity(stock.getCurrent_quantity())
                .cost_price(stock.getCost_price())
                .entry_date(stock.getEntry_date())
                .itemDTO(itemMapper.toDTO(stock.getItem()))
                .build();
    }

    public Stock toEntity(StockDTO dto) {
        if (dto == null) return null;

        return Stock.builder()
                .id(dto.getId())
                .initial_quantity(dto.getInitial_quantity())
                .current_quantity(dto.getCurrent_quantity())
                .cost_price(dto.getCost_price())
                .entry_date(dto.getEntry_date())
                .item(itemMapper.toEntity(dto.getItemDTO()))
                .build();
    }

    public Stock toEntity(StockCreateDTO dto){
        if (dto == null) return null;

        return Stock.builder()
                .initial_quantity(dto.getInitial_quantity())
                .current_quantity(dto.getCurrent_quantity())
                .cost_price(dto.getCost_price())
                .entry_date(dto.getEntry_date())
                .item(Item.builder()
                        .id(dto.getItemId())
                        .build())
                .build();
    }

    public Stock toEntity(StockUpdateDTO dto){
        if (dto == null) return null;

        return Stock.builder()
                .initial_quantity(dto.getInitial_quantity())
                .current_quantity(dto.getCurrent_quantity())
                .cost_price(dto.getCost_price())
                .entry_date(dto.getEntry_date())
                .item(Item.builder()
                        .id(dto.getItemId())
                        .build())
                .build();
    }

}
