package ru.chiffa.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.chiffa.DTO.ProductDto;
import ru.chiffa.utils.ProductDtoMapper;
import ru.chiffa.model.Product;
import ru.chiffa.DTO.ProductPageDto;
import ru.chiffa.reposirories.ProductRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductDtoMapper productDtoMapper;

    public ProductPageDto findAll(int pageNumber, int size) {
        Page<Product> page = productRepository.findAll(PageRequest.of(pageNumber, size));
        return new ProductPageDto(
                page.getContent().stream()
                        .map(productDtoMapper::productToProductDto)
                        .collect(Collectors.toList()),
                page.getNumber(),
                page.getTotalPages());
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public ProductDto save(ProductDto productDto) {
        return productDtoMapper.productToProductDto(
                productRepository.save(
                        productDtoMapper.productDtoToProduct(productDto)));
    }
}
