package ru.chiffa.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.chiffa.DTO.CartItemDto;
import ru.chiffa.model.CartItemIdentity;
import ru.chiffa.services.CartService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public List<CartItemDto> findCart(Principal principal) {
        return cartService.findByUsername(principal.getName());
    }

    @PostMapping
    public void saveOrUpdate(
            Principal principal,
            @RequestParam Long product,
            @RequestParam Integer quantity) {
        cartService.saveOrUpdate(principal.getName(), product, quantity);
    }

    @DeleteMapping
    public void deleteCartItem(Principal principal, @RequestParam Long productId) {
        cartService.deleteCartItem(principal.getName(), productId);
    }

    @DeleteMapping("/all")
    public void clearCart(Principal principal) {
        cartService.clearCart(principal.getName());
    }
}
