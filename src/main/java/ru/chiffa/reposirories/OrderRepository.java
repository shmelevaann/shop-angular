package ru.chiffa.reposirories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chiffa.dto.OrderDto;
import ru.chiffa.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserUsername(String username);
}
