package ru.chiffa.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.chiffa.DTO.ProductDto;
import ru.chiffa.DTO.ProductPageDto;
import ru.chiffa.services.ProductService;

@RestController
@RequestMapping("/products")
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