package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.UserDao;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    @Override
    public User create(User user) {
        String query =
                "INSERT INTO users (name, login, password, salt) VALUES (?, ?, ?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            var preparedStatement =
                    connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setBytes(4, user.getSalt());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
            setRolesToUser(user);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create user: ", e);
        }
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            var preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                var user = getUserFromResultSet(resultSet);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get user with ID = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM users";
        try (Connection connection = ConnectionUtil.getConnection()) {
            var preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                var user = getUserFromResultSet(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get all users ", e);
        }
    }

    @Override
    public User update(User user) {
        String query = "UPDATE TABLE users SET name = ?, user = ?, password = ?, salt = ? "
                + "WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            var preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setBytes(4, user.getSalt());
            deleteRolesOfUser(user.getId());
            setRolesToUser(user);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update user  = " + user.toString() + ": ", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM users WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            deleteRolesOfUser(id);
            var preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to delete user with id = " + id + ": ", e);
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        Long userId = resultSet.getLong("user_id");
        String name = resultSet.getString("name");
        String login = resultSet.getString("login");
        byte[] salt = resultSet.getBytes("salt");
        String password = resultSet.getString("password");
        User user = new User(name, login, password);
        user.setId(userId);
        user.setSalt(salt);
        user.setRoles(getUsersRoleByUserId(user.getId()));
        return user;
    }

    private Set<Role> getUsersRoleByUserId(Long id) throws SQLException {
        String query = "SELECT role_name FROM users_roles "
                + "JOIN roles USING (role_id) "
                + "WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            var preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Set<Role> roles = new HashSet<>();
            while (resultSet.next()) {
                roles.add(Role.of(resultSet.getString("role_name")));
            }
            return roles;
        }
    }

    private void setRolesToUser(User user) throws SQLException {
        String getRoleIdQuery = "SELECT role_id FROM roles WHERE role_name = ?;";
        String insertRolesToUserQuery
                = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?); ";
        for (Role role : user.getRoles()) {
            try (Connection connection = ConnectionUtil.getConnection()) {
                var getRoleIdStatement = connection.prepareStatement(getRoleIdQuery);
                getRoleIdStatement.setString(1, role.getRoleName().name());
                ResultSet resultSet = getRoleIdStatement.executeQuery();
                if (resultSet.next()) {
                    var insertRolesToUserStatement =
                            connection.prepareStatement(insertRolesToUserQuery);
                    insertRolesToUserStatement.setLong(1, user.getId());
                    insertRolesToUserStatement.setLong(2, resultSet.getLong("role_id"));
                    insertRolesToUserStatement.executeUpdate();
                }
            }
        }
    }

    private void deleteRolesOfUser(Long id) throws SQLException {
        String query = "DELETE FROM users_roles WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            var preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT * FROM users WHERE login  = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            var preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                var user = getUserFromResultSet(resultSet);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cant't retrieve user by login", e);
        }
        return Optional.empty();
    }
}
