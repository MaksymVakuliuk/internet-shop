package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.UserDao;
import com.internet.shop.db.Storage;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import com.internet.shop.util.ConnectionUtil;
import java.sql.*;
import java.util.*;
import java.util.stream.IntStream;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    @Override
    public User create(User user) {
       String insertUserParametersQuery
               = "INSERT INTO users (name, login, password) VALUES (?, ?, ?);";
       try (Connection connection = ConnectionUtil.getConnection()) {
           PreparedStatement createUserStatement =
                   connection.prepareStatement(insertUserParametersQuery, PreparedStatement.RETURN_GENERATED_KEYS);
           createUserStatement.setString(1, user.getName());
           createUserStatement.setString(2, user.getLogin());
           createUserStatement.setString(3, user.getPassword());
           createUserStatement.executeUpdate();
           ResultSet resultSet = createUserStatement.getGeneratedKeys();
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
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
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
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get all users ", e);
        }
    }

    @Override
    public User update(User user) {
        String updateUserQuery = "UPDATE TABLE users SET name = ?, user = ?, password = ? WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement updateUserStatement = connection.prepareStatement(updateUserQuery);
            updateUserStatement.setString(1, user.getName());
            updateUserStatement.setString(2, user.getLogin());
            updateUserStatement.setString(3, user.getPassword());
            deleteRolesOfUser(user.getId());
            setRolesToUser(user);
            return user;
        } catch (SQLException  e) {
            throw new RuntimeException("Unable to update user  = " + user.toString() + ": ", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteUserQuery = "DELETE FROM users WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            deleteRolesOfUser(id);
            PreparedStatement deleteUserStatement
                    = connection.prepareStatement(deleteUserQuery);
            deleteUserStatement.setLong(1, id);
            return deleteUserStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to delete user with id = " + id + ": " , e);
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        Long userID = resultSet.getLong("user_id");
        String name = resultSet.getString("name");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        User user = new User(name, login, password);
        user.setId(userID);
        user.setRoles(getUsersRoleByUserId(user.getId()));
        return user;
    }

    private Set<Role> getUsersRoleByUserId(Long id) throws SQLException {
        String query = "SELECT role_name FROM users_roles " +
                "JOIN roles USING (role_id) " +
                "WHERE user_id = ?;";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
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
        try (Connection connection = ConnectionUtil.getConnection()) {
            for (Role role : user.getRoles()) {
                PreparedStatement getRoleIdStatement
                        = connection.prepareStatement(getRoleIdQuery);
                getRoleIdStatement.setString(1, role.getRoleName().name());
                ResultSet resultSet = getRoleIdStatement.executeQuery();
                if (resultSet.next()) {
                    PreparedStatement insertRolesToUserStatement
                            = connection.prepareStatement(insertRolesToUserQuery);
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
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
