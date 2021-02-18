package ru.chiffa.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.chiffa.dto.OrderDto;
import ru.chiffa.dto.OrderItemDto;
import ru.chiffa.model.Order;
import ru.chiffa.model.OrderItem;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderDtoMapper {
    private final ProductDtoMapper productDtoMapper;

    public OrderDto orderToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserId(order.getUser().getId());
        orderDto.setDate(order.getDate());
        orderDto.setItems(order.getItems().stream()
                .map(this::orderItemToOrderItemDto)
                .collect(Collectors.toList()));
        return orderDto;
    }

    public OrderItemDto orderItemToOrderItemDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProduct(productDtoMapper.productToProductDto(orderItem.getProduct()));
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
        return orderItemDto;
    }
}
