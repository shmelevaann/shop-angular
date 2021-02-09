package ru.chiffa.reposirories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chiffa.model.OrderItem;
import ru.chiffa.model.OrderItemIdentity;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemIdentity> {
}
