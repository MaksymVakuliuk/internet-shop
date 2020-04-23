package com.internet.shop.dao.impl;

import com.internet.shop.dao.OrderDao;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
import com.internet.shop.model.User;
import java.util.List;

@Dao
public class OrderDaoImpl implements OrderDao {

    @Override
    public Order completeOrder(List<Product> products, User user) {
        return null;
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return null;
    }

    @Override
    public Order get(Long id) {
        return null;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
