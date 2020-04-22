package com.internet.shop.dao;

import com.internet.shop.model.ShoppingCart;

public interface ShoppingCartDao {
    ShoppingCart create(ShoppingCart shoppingCart);

    ShoppingCart get(Long bucketId);

    ShoppingCart update(ShoppingCart shoppingCart);
}
