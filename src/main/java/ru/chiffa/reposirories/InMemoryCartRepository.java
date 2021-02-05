package ru.chiffa.reposirories;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.WebApplicationContext;
import ru.chiffa.model.CartItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class InMemoryCartRepository {
    private final List<CartItem> cartItems = new ArrayList<>();

    public void save(CartItem cartItem) {
        Optional<CartItem> existingItem = cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(cartItem.getProduct().getId()))
                .findFirst();

        if(existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(cartItem.getQuantity()+item.getQuantity());
        } else {
            cartItems.add(cartItem);
        }
    }

    public void deleteById(Long productId) {
        cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(cartItems::remove);
    }

    public void deleteAll() {
        cartItems.clear();
    }

    public List<CartItem> findAll() {
        return cartItems;
    }
}
