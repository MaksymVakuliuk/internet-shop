package com.internet.shop.model;

import java.util.List;

public class ShoppingCart {
    private Long id;
    private List<Product> products;
    private Long orderId;

    public ShoppingCart(List<Product> products, Long orderId) {
        this.products = products;
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Long getOrderId() {
        return orderId;
    }
}
