package com.internet.shop.model;

import java.util.List;

public class ShoppingCart {
    private Long id;
    private User user;
    private List<Product> products;

    public ShoppingCart(User user, List<Product> products) {
        this.user = user;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public List<Product> getProducts() {
        return products;
    }
}
