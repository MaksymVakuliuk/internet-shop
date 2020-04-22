package com.internet.shop.service;

import com.internet.shop.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart addItem(Long bucketId, Long itemId);
}
