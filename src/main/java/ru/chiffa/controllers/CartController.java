package ru.chiffa.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.chiffa.dto.CartItemDto;
import ru.chiffa.services.CartService;
import ru.chiffa.services.InMemoryCartService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final InMemoryCartService inMemoryCartService;

    @GetMapping
    public List<CartItemDto> findCart(Principal principal) {
        if (principal == null) {
            return inMemoryCartService.findAll();
        } else {
            return cartService.findByUsername(principal.getName());
        }
    }

    @PostMapping
    public void saveOrUpdate(
            Principal principal,
            @RequestParam Long product,
            @RequestParam Integer quantity) {
        if(principal == null) {
            inMemoryCartService.save(product, quantity);
        } else {
            cartService.saveOrUpdate(principal.getName(), product, quantity);
        }
    }

    @DeleteMapping
    public void deleteCartItem(Principal principal, @RequestParam Long productId) {
        if (principal == null) {
            inMemoryCartService.deleteById(productId);
        } else {
            cartService.deleteCartItem(principal.getName(), productId);
        }
    }

    @DeleteMapping("/all")
    public void clearCart(Principal principal) {
        if (principal == null) {
            inMemoryCartService.clearCart();
        } else {
            cartService.clearCart(principal.getName());
        }
    }

    @PostMapping("/checkout")
    public void checkOut(Principal principal) {
        cartService.checkOut(principal.getName());
    }
}
