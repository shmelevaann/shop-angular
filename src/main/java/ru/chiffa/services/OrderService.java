package ru.chiffa.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chiffa.dto.OrderDto;
import ru.chiffa.model.Order;
import ru.chiffa.model.OrderItem;
import ru.chiffa.model.User;
import ru.chiffa.reposirories.OrderItemRepository;
import ru.chiffa.reposirories.OrderRepository;
import ru.chiffa.utils.OrderDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final OrderDtoMapper orderDtoMapper;

    public void handleOrder(List<OrderItem> orderItems, Long userId) {
        Order order = createOrder(userId);
        orderItemRepository.saveAll(
                orderItems.stream()
                        .peek(item -> item.setOrder(order)).collect(Collectors.toList()));
    }

    private Order createOrder(Long userId) {
        Order order = new Order();
        order.setUser(new User());
        order.getUser().setId(userId);
        order = orderRepository.save(order);
        return order;
    }

    public List<OrderDto> findALl(String username) {
        return orderRepository.findAllByUserUsername(username).stream()
                .map(orderDtoMapper::orderToOrderDto)
                .collect(Collectors.toList());
    }
}
