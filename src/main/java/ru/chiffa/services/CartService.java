package ru.chiffa.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chiffa.DTO.CartItemDto;
import ru.chiffa.DTO.CartItemDtoMapper;
import ru.chiffa.model.CartItem;
import ru.chiffa.model.CartItemIdentity;
import ru.chiffa.reposirories.CartRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemDtoMapper cartItemDtoMapper;

    public List<CartItemDto> findByUserId(Long id) {
        return cartRepository.findAllByUserId(id).stream().map(cartItemDtoMapper::cartItemToCartItemDto).collect(Collectors.toList());
    }

    public void saveOrUpdate(CartItemDto cartItemDto) {
        CartItem cartItem = cartItemDtoMapper.cartItemDtoToCartItem(cartItemDto);
        cartRepository.findById(cartItem.getId())
                .ifPresent(existingItem -> cartItem.setQuantity(cartItem.getQuantity() + existingItem.getQuantity()));
        cartRepository.save(cartItem);
    }

    public void deleteCartItem(CartItemIdentity cartItemId) {
        cartRepository.deleteById(cartItemId);
    }


    //Вы могли бы объяснить почему без такой аннотации выпадает нижеследующее?
    //"No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call"
    //stackOverflow проблему решил, но почему так я не поняла..
    //https://stackoverflow.com/questions/32269192/spring-no-entitymanager-with-actual-transaction-available-for-current-thread
    @Transactional
    public void clearCart(Long userId) {
        cartRepository.deleteByUserId(userId);
    }
}
