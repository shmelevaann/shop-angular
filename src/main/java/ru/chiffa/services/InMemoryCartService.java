package ru.chiffa.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chiffa.dto.CartItemDto;
import ru.chiffa.exceptions.ResourceNotFoundException;
import ru.chiffa.model.CartItem;
import ru.chiffa.model.Product;
import ru.chiffa.reposirories.InMemoryCartRepository;
import ru.chiffa.reposirories.ProductRepository;
import ru.chiffa.utils.CartItemDtoMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InMemoryCartService {
    private final InMemoryCartRepository inMemoryCartRepository;
    private final ProductRepository productRepository;
    private final CartItemDtoMapper cartItemDtoMapper;

    public void save(Long productId, Integer quantity) {
        CartItem cartItem = new CartItem();
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }
        cartItem.setProduct(product.get());
        cartItem.setQuantity(quantity);
        inMemoryCartRepository.saveOrUpadate(cartItem);
    }

    public List<CartItemDto> findAll() {
        return inMemoryCartRepository.findAll().stream()
                .map(cartItemDtoMapper::cartItemToCartItemDto)
                .collect(Collectors.toList());
    }

    public void deleteById(Long productId) {
        inMemoryCartRepository.deleteById(productId);
    }

    public void clearCart() {
        inMemoryCartRepository.deleteAll();
    }
}
