package com.internet.shop.dao.impl;

import com.internet.shop.db.Storage;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
import com.internet.shop.dao.ProductDao;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class ProductDaoImpl implements ProductDao {

    @Override
    public Product create(Product product) {
        return Storage.addProduct(product);
    }

    @Override
    public Optional<Product> get(Long id) {
        return Storage.PRODUCTS
                .stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .or(Optional::empty);
    }

    @Override
    public List<Product> getAll() {
        return Storage.PRODUCTS;
    }

    @Override
    public Product update(Product product) {
        IntStream.range(0, Storage.PRODUCTS.size()).filter(i -> Storage.PRODUCTS.get(i).getId().equals(product.getId())).forEach(i -> Storage.PRODUCTS.set(i,product));
        return product;
    }

    @Override
    public boolean delete(Long id) {
        Storage.PRODUCTS.remove(get(id).orElseThrow(NoSuchElementException::new));
        return true;
    }
}
