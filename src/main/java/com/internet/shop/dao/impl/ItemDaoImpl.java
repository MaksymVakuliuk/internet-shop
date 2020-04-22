package com.internet.shop.dao.impl;

import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
import com.internet.shop.dao.ItemDao;

import java.util.List;
import java.util.Optional;

@Dao
public class ItemDaoImpl implements ItemDao {

    @Override
    public Product create(Product product) {
        return null;
    }

    @Override
    public Optional<Product> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public Product update(Product product) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
