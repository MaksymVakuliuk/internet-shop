package com.internet.shop.db;

import com.internet.shop.model.Order;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.Product;
import com.internet.shop.model.User;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static Long productId = 0L;
    private static Long shoppingCartId = 0L;
    private static Long orderId = 0L;
    private static Long userId = 0L;
    public static final List<Product> PRODUCTS = new ArrayList<>();
    public static final List<ShoppingCart> SHOPPING_CARTS = new ArrayList<>();
    public static final List<Order> ORDERS = new ArrayList<>();
    public static final List<User> USERS = new ArrayList<>();

    public static Product addProduct(Product product) {
        productId++;
        product.setId(productId);
        PRODUCTS.add(product);
        return product;
    };

}
