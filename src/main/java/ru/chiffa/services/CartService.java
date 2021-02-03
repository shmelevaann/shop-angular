package ru.chiffa.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chiffa.DTO.CartItemDto;
import ru.chiffa.model.OrderItem;
import ru.chiffa.model.Product;
import ru.chiffa.reposirories.UserRepository;
import ru.chiffa.utils.CartItemDtoMapper;
import ru.chiffa.model.CartItem;
import ru.chiffa.model.CartItemIdentity;
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
    private final OrderService orderService;

    public List<CartItemDto> findByUsername(String username) {
        return cartRepository.findAllByUserUsername(username).stream().map(cartItemDtoMapper::cartItemToCartItemDto).collect(Collectors.toList());
    }

    public void saveOrUpdate(String username, Long product, Integer quantity) {
        CartItem cartItem;
        Optional<CartItem> existingItem = cartRepository.findByUserUsernameAndProductId(username, product);
        if (existingItem.isPresent()) {
            cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = new CartItem();
            cartItem.setUser(userRepository.findByUsername(username).get());
            cartItem.setProduct(new Product());
            cartItem.getProduct().setId(product);
            cartItem.setQuantity(quantity);
        }

        if (cartItem.getQuantity() <= 0) {
            cartRepository.deleteById(new CartItemIdentity(cartItem.getUser().getId(), cartItem.getProduct().getId()));
        } else {
            cartRepository.save(cartItem);
        }
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
    public void checkOut(String username) {
        List<CartItem> cart = cartRepository
                .findAllByUserUsername(username);

        if(cart.isEmpty()) {
            return;
        }

        orderService.handleOrder(cart.stream()
                .map(this::cartItemToOrderItem)
                .collect(Collectors.toList()),
                cart.stream().findFirst().get().getUser().getId());
        clearCart(username);
    }

    private OrderItem cartItemToOrderItem(CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(cartItem.getProduct());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getProduct().getPrice());
        return orderItem;
    }
}
