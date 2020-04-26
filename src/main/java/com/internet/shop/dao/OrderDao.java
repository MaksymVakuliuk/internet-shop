package com.internet.shop.dao;

import com.internet.shop.model.Order;
import com.internet.shop.model.User;
import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Order create(Order order);

    List<Order> getUserOrders(User user);

    Optional<Order> get(Long id);

    List<Order> getAll();

    Order update(Order order);

    boolean delete(Long id);
}
