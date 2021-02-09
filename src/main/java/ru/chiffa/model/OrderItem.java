package ru.chiffa.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "order_items")
@IdClass(OrderItemIdentity.class)
public class OrderItem {
    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Integer price;
}
