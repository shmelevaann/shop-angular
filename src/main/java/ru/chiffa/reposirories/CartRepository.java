package ru.chiffa.reposirories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chiffa.model.CartItem;
import ru.chiffa.model.CartItemIdentity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItem, CartItemIdentity> {
    List<CartItem> findAllByUserUsername(String username);

    void deleteByUserUsername(String username);

    void deleteByUserUsernameAndProductId(String username, Long product);

    Optional<CartItem> findByUserUsernameAndProductId(String username, Long product);
}
