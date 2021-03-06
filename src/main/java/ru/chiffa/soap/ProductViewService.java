package ru.chiffa.soap;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chiffa.model.Product;
import ru.chiffa.reposirories.ProductRepository;
import ru.chiffa.soap.products.ProductView;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductViewService {
    private final ProductRepository productRepository;

    public List<ProductView> getAllProductViews() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::productToProductView).collect(Collectors.toList());
    }

    private ProductView productToProductView(Product product) {
        ProductView productView = new ProductView();
        productView.setId(product.getId());
        productView.setTitle(product.getTitle());
        productView.setPrice(product.getPrice());
        return productView;
    }
}
