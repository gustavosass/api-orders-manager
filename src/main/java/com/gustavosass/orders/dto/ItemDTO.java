package com.gustavosass.orders.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemDTO {

    Long id;
    String name;
    String description;
}
