package com.internet.shop.model;

import java.util.List;

public class Order {
    private Long id;
    private Long userID;
    private List<Product> products;

    public Order(Long userID, List<Product> products) {
        this.userID = userID;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userID;
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Order{"
                + "id=" + id
                + ", user=" + userID
                + ", products=" + products.stream().map(Product::toString) + '}';
    }
}
