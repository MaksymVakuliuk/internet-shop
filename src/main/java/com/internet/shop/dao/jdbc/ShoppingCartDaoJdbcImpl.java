package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.ProductDao;
import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        String query  = "INSERT INTO shopping_carts (user_id) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, shoppingCart.getUserId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                shoppingCart.setId(resultSet.getLong(1));
            }
            return shoppingCart;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create shopping cart: ", e);
        }
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        String getQuery = "SELECT * FROM shopping_carts WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement getStatement = connection.prepareStatement(getQuery);
            getStatement.setLong(1, id);
            ResultSet resultSet = getStatement.executeQuery();
            if (resultSet.next()) {
                ShoppingCart shoppingCart = getShoppingCartFromResultSet(resultSet);
                return Optional.of(shoppingCart);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get shopping cart with ID = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<ShoppingCart> getAll() {
        String getAllQuery = "SELECT * FROM shopping_carts;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement getAllStatement = connection.prepareStatement(getAllQuery);
            ResultSet resultSet = getAllStatement.executeQuery();
            List<ShoppingCart> shoppingCarts = new ArrayList<>();
            while (resultSet.next()) {
                ShoppingCart shoppingCart = getShoppingCartFromResultSet(resultSet);
                shoppingCarts.add(shoppingCart);
            }
            return shoppingCarts;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get all shopping carts : ", e);
        }
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        try {
            deleteShoppingCartProducts(shoppingCart.getId());
            insertShoppingCartProducts(shoppingCart);
            return shoppingCart;
        } catch (SQLException  e) {
            throw new RuntimeException("Unable to update shopping cart  = " + shoppingCart.toString() + ": ", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteUserQuery = "DELETE FROM shopping_carts WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            deleteShoppingCartProducts(id);
            PreparedStatement deleteUserStatement
                    = connection.prepareStatement(deleteUserQuery);
            deleteUserStatement.setLong(1, id);
            return deleteUserStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to delete user with id = " + id + ": " , e);
        }
    }

    private void insertShoppingCartProducts(ShoppingCart shoppingCart) throws SQLException {
        String insertCartProductsQuery =
                "INSERT INTO shopping_carts_products (cart_id, product_id) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement insertCartProductsStatement =
                    connection.prepareStatement(insertCartProductsQuery);
            for (Product product : shoppingCart.getProducts()) {
                insertCartProductsStatement.setLong(1, shoppingCart.getId());
                insertCartProductsStatement.setLong(2, product.getId());
                insertCartProductsStatement.executeUpdate();
            }
        }
    }

    private void deleteShoppingCartProducts(Long cartID) throws SQLException {
        String deleteCartProductsQuery =
                "DELETE FROM shopping_carts_products WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
                PreparedStatement insertCartProductsStatement =
                        connection.prepareStatement(deleteCartProductsQuery);
                insertCartProductsStatement.setLong(1, cartID);
                insertCartProductsStatement.executeUpdate();
        }
    }

    private ShoppingCart getShoppingCartFromResultSet(ResultSet resultSet) throws SQLException {
        Long cartID = resultSet.getLong("cart_id");
        Long userID = resultSet.getLong("user_id");
        List<Product> products = getProductsOfCart(cartID);
        ShoppingCart shoppingCart = new ShoppingCart(userID);
        shoppingCart.setId(cartID);
        shoppingCart.setProducts(products);
        return shoppingCart;
    }

    private List<Product> getProductsOfCart(Long cartID) throws SQLException {
        String getProductsOfSCartQuery = "SELECT product_id FROM products "
                + "JOIN shopping_carts_products USING(product_id) " +
                "WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement getProductsOfCartStatement =
                    connection.prepareStatement(getProductsOfSCartQuery);
            getProductsOfCartStatement.setLong(1, cartID);
            ResultSet resultSet = getProductsOfCartStatement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                ProductDao productDao = new ProductDaoJdbcImpl();
                Product product =  productDao.get(resultSet.getLong("product_id")).get();
                products.add(product);
            }
            return  products;
        }
    }

}
