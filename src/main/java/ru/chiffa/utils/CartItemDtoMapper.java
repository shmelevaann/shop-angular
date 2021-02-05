package ru.chiffa.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.chiffa.dto.CartItemDto;
import ru.chiffa.model.CartItem;
import ru.chiffa.model.User;

@Component
@RequiredArgsConstructor
public class CartItemDtoMapper {
    private final ProductDtoMapper productDtoMapper;

    public CartItemDto cartItemToCartItemDto(CartItem cartItem) {
        CartItemDto dto = new CartItemDto();
        dto.setUserId(cartItem.getUser().getId());
        dto.setProduct(productDtoMapper.productToProductDto(cartItem.getProduct()));
        dto.setQuantity(cartItem.getQuantity());
        return dto;
    }

    public CartItem cartItemDtoToCartItem(CartItemDto dto) {
        CartItem cartItem = new CartItem();
        cartItem.setUser(new User());
        cartItem.getUser().setId(dto.getUserId());
        cartItem.setProduct(productDtoMapper.productDtoToProduct(dto.getProduct()));
        cartItem.setQuantity(dto.getQuantity());
        return cartItem;
    }
}
