package ru.chiffa.model;

import lombok.Data;

import java.util.List;

@Data
public class ProductPage {
    private final List<Product> products;
    private final int currentPage;
    private final int totalPages;

}
