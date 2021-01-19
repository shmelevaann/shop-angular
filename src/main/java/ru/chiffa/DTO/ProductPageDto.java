package ru.chiffa.DTO;

import lombok.Data;
import ru.chiffa.model.Product;

import java.util.List;

@Data
public class ProductPageDto {
    private final List<ProductDto> products;
    private final int currentPage;
    private final int totalPages;

}
