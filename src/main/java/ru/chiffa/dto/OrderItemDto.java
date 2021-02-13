package ru.chiffa.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private ProductDto product;
    private Integer quantity;
    private Integer price;
}
