package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.OrderDao;
import com.internet.shop.dao.ProductDao;
import com.internet.shop.lib.Dao;
import com.internet.shop.lib.Inject;
import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {
    @Inject
    private ProductDao productDao;

    @Override
    public Order create(Order order) {
        String query = "INSERT INTO orders (user_id) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            var preparedStatement =
                    connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                order.setId(resultSet.getLong(1));
            }
            insertOrderCartProducts(order);
            return order;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create order: ", e);
        }
    }

    @Override
    public Optional<Order> get(Long id) {
        String query = "SELECT * FROM orders WHERE order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            var preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                var order = getOrderFromResultSet(resultSet);
                return Optional.of(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get order with ID = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Order> getAll() {
        String query = "SELECT * FROM orders;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            var preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                var order = getOrderFromResultSet(resultSet);
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get all orders carts : ", e);
        }
    }

    @Override
    public Order update(Order order) {
        try {
            deleteOrderProducts(order.getId());
            insertOrderCartProducts(order);
            return order;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update order cart  = "
                    + order.toString() + ": ", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM orders WHERE order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            deleteOrderProducts(id);
            var preparedStatement
                    = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to delete order with id = " + id + ": ", e);
        }
    }

    private void insertOrderCartProducts(Order order) throws SQLException {
        String query =
                "INSERT INTO orders_products (order_id, product_id) VALUES (?, ?);";
        for (Product product : order.getProducts()) {
            try (Connection connection = ConnectionUtil.getConnection()) {
                var preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(1, order.getId());
                preparedStatement.setLong(2, product.getId());
                preparedStatement.executeUpdate();
            }
        }

    }

    private void deleteOrderProducts(Long orderId) throws SQLException {
        String query = "DELETE FROM orders_products WHERE order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            var preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, orderId);
            preparedStatement.executeUpdate();
        }
    }

    private Order getOrderFromResultSet(ResultSet resultSet) throws SQLException {
        Long orderId = resultSet.getLong("order_id");
        Long userId = resultSet.getLong("user_id");
        List<Product> products = getProductsOfOrder(orderId);
        Order order = new Order(userId, products);
        order.setId(orderId);
        return order;
    }

    private List<Product> getProductsOfOrder(Long orderId) throws SQLException {
        String query = "SELECT product_id FROM products "
                + "JOIN orders_products USING(product_id) "
                + "WHERE order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            var preparedStatement =
                    connection.prepareStatement(query);
            preparedStatement.setLong(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                var product = productDao.get(resultSet.getLong("product_id")).get();
                products.add(product);
            }
            return products;
        }
    }
}
