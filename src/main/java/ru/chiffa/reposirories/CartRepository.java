package ru.chiffa.reposirories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chiffa.model.CartItem;
import ru.chiffa.model.CartItemIdentity;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItem, CartItemIdentity> {
    List<CartItem> findAllByUserId(Long id);

    void deleteByUserId(Long userId);
}
