package ru.chiffa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemIdentity implements Serializable {
    private Long user;
    private Long product;
}
