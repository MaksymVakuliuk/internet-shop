package com.internet.shop.dao.jdbc;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import com.internet.shop.dao.ProductDao;
import com.internet.shop.exceptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ProductDaoJdbcImpl implements ProductDao {
    @Override
    public Product create(Product product) {
        String query = "INSERT INTO products (name, price) VALUES (? , ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                var preparedStatement =
                        connection.prepareStatement(query, RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                product.setId(resultSet.getLong(1));
            }
            return product;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to create product: " + product, e);
        }
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT * FROM products WHERE product_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Product product = getProductFromResultSet(resultSet);
                return Optional.of(product);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to get product with ID = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products";
        try (Connection connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = getProductFromResultSet(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable get all products: ", e);
        }
    }

    @Override
    public Product update(Product product) {
        String query = "UPDATE products SET name = ?, price = ? WHERE product_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setLong(3, product.getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                product.setId(resultSet.getLong("product_id"));
            }
            return product;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to update product: " + product, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteProductFromProductsQuery = "DELETE FROM products WHERE product_id = ?;";
        String deleteProductsFromOrdersQuery = "DELETE FROM orders_products WHERE product_id = ?;";
        String deleteProductFromCartsQuery =
                "DELETE FROM shopping_carts_products WHERE product_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                var deleteProductFromCartsStatement =
                        connection.prepareStatement(deleteProductFromCartsQuery);) {
            deleteProductFromCartsStatement.setLong(1, id);
            deleteProductFromCartsStatement.executeUpdate();
            var deleteProductFromOrdersStatement =
                    connection.prepareStatement(deleteProductsFromOrdersQuery);
            deleteProductFromOrdersStatement.setLong(1, id);
            deleteProductFromOrdersStatement.executeUpdate();
            var deleteProductsFromProductsStatement =
                    connection.prepareStatement(deleteProductFromProductsQuery);
            deleteProductsFromProductsStatement.setLong(1, id);
            return deleteProductsFromProductsStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to delete product with ID = " + id, e);
        }
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Long productId = resultSet.getLong("product_id");
        String name = resultSet.getString("name");
        Double price = resultSet.getDouble("price");
        var product = new Product(name, price);
        product.setId(productId);
        return product;
    }
}
