package ru.chiffa.DTO;

import org.springframework.stereotype.Component;
import ru.chiffa.model.Product;

@Component
public class ProductDtoMapper {
    public ProductDto productToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setPrice(product.getPrice());
        return productDto;
    }

    public Product productDtoToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        return product;
    }
}
