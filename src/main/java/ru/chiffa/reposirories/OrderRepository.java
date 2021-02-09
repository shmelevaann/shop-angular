package ru.chiffa.reposirories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chiffa.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
