package ru.chiffa.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.chiffa.dto.ProductDto;
import ru.chiffa.dto.ProductPageDto;
import ru.chiffa.services.ProductService;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ProductPageDto findAll(@RequestParam Integer page, @RequestParam Integer size) {
        return productService.findAll(page, size);
    }

    @PostMapping
    public ProductDto save(@RequestBody ProductDto productDto) {
        System.out.println(productDto);
        return productService.save(productDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
