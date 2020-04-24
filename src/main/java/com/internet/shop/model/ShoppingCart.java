package com.internet.shop.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private Long id;
    private User user;
    private List<Product> products = new ArrayList<>();

    public ShoppingCart(User user) {
        this.user = user;
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

    @Override
    public String toString() {
        return "ShoppingCart{"
                + "id=" + id
                + ", user=" + user
                + ", products=" + products.stream().map(Product::toString) + '}';
    }
}
