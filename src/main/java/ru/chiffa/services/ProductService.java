package ru.chiffa.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.chiffa.model.Product;
import ru.chiffa.model.ProductPage;
import ru.chiffa.reposirories.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductPage findAll(int pageNumber, int size) {
        Page<Product> page = productRepository.findAll(PageRequest.of(pageNumber, size));
        return new ProductPage(page.getContent(), page.getNumber(), page.getTotalPages());
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
