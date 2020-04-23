package com.internet.shop.dao.impl;

import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import java.util.List;

@Dao
public class ShoppingCartDaoImpl implements ShoppingCartDao {

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        return null;
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        return false;
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {

    }

    @Override
    public ShoppingCart getByUserId(Long userId) {
        return null;
    }

    @Override
    public List<Product> getAllProducts(ShoppingCart shoppingCart) {
        return null;
    }
}
