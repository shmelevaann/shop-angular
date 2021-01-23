package ru.chiffa.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemDto {
    private Long userId;
    private ProductDto product;
    private Integer quantity;
}
