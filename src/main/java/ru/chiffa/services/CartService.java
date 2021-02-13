package ru.chiffa.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chiffa.dto.CartItemDto;
import ru.chiffa.exceptions.MarketError;
import ru.chiffa.exceptions.ResourceNotFoundException;
import ru.chiffa.model.*;
import ru.chiffa.reposirories.InMemoryCartRepository;
import ru.chiffa.reposirories.UserRepository;
import ru.chiffa.utils.CartItemDtoMapper;
import ru.chiffa.reposirories.CartRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemDtoMapper cartItemDtoMapper;
    private final UserRepository userRepository;
    private final UserService userService;
    private final OrderService orderService;
    private final InMemoryCartRepository inMemoryCartRepository;

    public List<CartItemDto> findByUsername(String username) {
        return cartRepository.findAllByUserUsername(username).stream().map(cartItemDtoMapper::cartItemToCartItemDto).collect(Collectors.toList());
    }

    public void saveOrUpdate(String username, Long product, Integer quantity) {
        Optional<CartItem> existingItem = cartRepository.findByUserUsernameAndProductId(username, product);
        if (existingItem.isPresent()) {
            update(quantity, existingItem.get());
        } else if (quantity > 0) {
            save(username, product, quantity);
        }
    }

    private void update(Integer quantity, CartItem cartItem) {
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        if (cartItem.getQuantity() <= 0) {
            cartRepository.deleteById(new CartItemIdentity(cartItem.getUser().getId(), cartItem.getProduct().getId()));
        } else {
            cartRepository.save(cartItem);
        }
    }

    private void save(String username, Long product, Integer quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setUser(userService.findUserByUsername(username));
        cartItem.setProduct(new Product());
        cartItem.getProduct().setId(product);
        cartItem.setQuantity(quantity);

        cartRepository.save(cartItem);
    }

    @Transactional
    public void deleteCartItem(String username, Long product) {
        cartRepository.deleteByUserUsernameAndProductId(username, product);
    }


    @Transactional
    public void clearCart(String username) {
        cartRepository.deleteByUserUsername(username);
    }

    @Transactional
    public void checkOut(String username, Long address) {
        List<CartItem> cart = cartRepository
                .findAllByUserUsername(username);

        if (cart.isEmpty()) {
            return;
        }

        orderService.handleOrder(
                cart.stream()
                        .map(this::cartItemToOrderItem)
                        .collect(Collectors.toList()),
                cart.stream().findFirst().get().getUser().getId(),
                address);
        clearCart(username);
    }

    private OrderItem cartItemToOrderItem(CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(cartItem.getProduct());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getProduct().getPrice());
        return orderItem;
    }

    public void handleCarts(String username) {
        List<CartItem> inMemoryCart = inMemoryCartRepository.findAll();
        if (!inMemoryCart.isEmpty()) {
            consolidateCarts(inMemoryCart, username);
        }
    }

    private void consolidateCarts(List<CartItem> inMemoryCart, String username) {
        User user = userService.findUserByUsername(username);
        inMemoryCart.forEach(item -> item.setUser(user));

        cartRepository.saveAll(inMemoryCart);
        inMemoryCartRepository.deleteAll();
    }
}
