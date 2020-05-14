package com.internet.shop.service.impl;

import com.internet.shop.dao.OrderDao;
import com.internet.shop.db.Storage;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
import com.internet.shop.model.User;
import com.internet.shop.service.OrderService;
import com.internet.shop.service.ShoppingCartService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private OrderDao orderDao;
    @Inject
    private ShoppingCartService shoppingCartService;

    @Override
    public Order completeOrder(List<Product> products, Long userId) {
        var order = orderDao.create(new Order(userId, List.copyOf(products)));
        return order;
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return orderDao.getAll()
                .stream()
                .filter(order -> order.getUserId().equals(user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Order create(Order order) {
        return Storage.addOrder(order);
    }

    @Override
    public Order get(Long id) {
        return orderDao.get(id).get();
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public Order update(Order order) {
        IntStream.range(0, Storage.orders.size())
                .filter(i -> Storage.orders.get(i)
                        .getId()
                        .equals(order.getId()))
                .forEach(i -> Storage.orders.set(i, order));
        return order;
    }

    @Override
    public boolean delete(Long id) {
        return orderDao.delete(id);
    }
}
