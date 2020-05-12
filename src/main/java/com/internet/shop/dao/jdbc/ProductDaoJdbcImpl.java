package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.ProductDao;
import com.internet.shop.lib.Dao;
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
public class ProductDaoJdbcImpl implements ProductDao {
    @Override
    public Product create(Product product) {
        String query = "INSERT INTO products (name, price) VALUES (? , ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                product.setId(resultSet.getLong("product_id"));
            }
            return product;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create product: " + product, e);
        }
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT * FROM products WHERE product_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Product product = getProductFromResultSet(resultSet);
                return Optional.of(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get product with ID = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products";
        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = getProductFromResultSet(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Unable get all products: ", e);
        }
    }

    @Override
    public Product update(Product product) {
        String query = "UPDATE products SET NAME = ?, PRICE = ? WHERE product_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setLong(2, product.getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                product.setId(resultSet.getLong("product_id"));
            }
            return product;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update product: " + product, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE products WHERE product_id = ?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to delete product with ID =" + id, e);
        }
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Long productID = resultSet.getLong("product_id");
        String name = resultSet.getString("name");
        Double price = resultSet.getDouble("price");
        Product product = new Product(name, price);
        product.setId(productID);
        return product;
    }
}
