package com.gustavosass.orders.mapper;

import com.gustavosass.orders.dto.ItemCreateDTO;
import com.gustavosass.orders.dto.ItemDTO;
import com.gustavosass.orders.dto.ItemUpdateDTO;
import com.gustavosass.orders.model.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public Item toEntity(ItemDTO dto){
        return Item.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public Item toEntity(ItemCreateDTO dto){
        return Item.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public Item toEntity(ItemUpdateDTO dto){
        return Item.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public ItemDTO toDto(Item item){
        return ItemDTO.builder()
                .name(item.getName())
                .description(item.getDescription())
                .build();
    }
}
