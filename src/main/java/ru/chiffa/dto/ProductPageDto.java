package ru.chiffa.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductPageDto {
    private final List<ProductDto> products;
    private final int currentPage;
    private final int totalPages;

}
