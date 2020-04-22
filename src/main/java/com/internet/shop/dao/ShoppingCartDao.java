package com.internet.shop.dao;

import com.internet.shop.model.ShoppingCart;

public interface ShoppingCartDao {
    ShoppingCart create(ShoppingCart shoppingCart);

    ShoppingCart get(Long BucketId);

    ShoppingCart update(ShoppingCart shoppingCart);
}
