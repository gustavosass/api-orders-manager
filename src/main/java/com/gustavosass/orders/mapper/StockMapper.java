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
                .initialQuantity(stock.getInitialQuantity())
                .currentQuantity(stock.getCurrentQuantity())
                .costPrice(stock.getCostPrice())
                .entryDate(stock.getEntryDate())
                .itemDTO(itemMapper.toDTO(stock.getItem()))
                .build();
    }

    public Stock toEntity(StockDTO dto) {
        if (dto == null) return null;

        return Stock.builder()
                .id(dto.getId())
                .initialQuantity(dto.getInitialQuantity())
                .currentQuantity(dto.getCurrentQuantity())
                .costPrice(dto.getCostPrice())
                .entryDate(dto.getEntryDate())
                .item(itemMapper.toEntity(dto.getItemDTO()))
                .build();
    }

    public Stock toEntity(StockCreateDTO dto){
        if (dto == null) return null;

        return Stock.builder()
                .initialQuantity(dto.getInitial_quantity())
                .currentQuantity(dto.getCurrent_quantity())
                .costPrice(dto.getCost_price())
                .entryDate(dto.getEntry_date())
                .item(Item.builder()
                        .id(dto.getItemId())
                        .build())
                .build();
    }

    public Stock toEntity(StockUpdateDTO dto){
        if (dto == null) return null;

        return Stock.builder()
                .initialQuantity(dto.getInitial_quantity())
                .currentQuantity(dto.getCurrent_quantity())
                .costPrice(dto.getCost_price())
                .entryDate(dto.getEntry_date())
                .item(Item.builder()
                        .id(dto.getItemId())
                        .build())
                .build();
    }

}
