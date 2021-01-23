package ru.chiffa.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.chiffa.DTO.CartItemDto;
import ru.chiffa.model.CartItem;
import ru.chiffa.model.CartItemIdentity;
import ru.chiffa.services.CartService;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public List<CartItemDto> findByUserId(@RequestParam Long id) {
        return cartService.findByUserId(id);
    }

    @PostMapping
    public void saveOrUpdate(@RequestBody CartItemDto cartItemDto) {
        cartService.saveOrUpdate(cartItemDto);
    }

    @DeleteMapping
    public void deleteCartItem(@RequestParam Long userId, @RequestParam Long productId) {
        cartService.deleteCartItem(new CartItemIdentity(userId, productId));
    }

    @DeleteMapping("/all")
    public void clearCart(@RequestParam Long userId) {
        cartService.clearCart(userId);
    }
}
