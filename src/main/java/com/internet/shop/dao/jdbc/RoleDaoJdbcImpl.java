package com.internet.shop.dao.jdbc;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import com.internet.shop.dao.RoleDao;
import com.internet.shop.exceptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Role;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class RoleDaoJdbcImpl implements RoleDao {
    @Override
    public Role create(Role role) {
        String query = "INSERT INTO roles (role_name) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection();
                var preparedStatement =
                        connection.prepareStatement(query, RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, role.getRoleName().name());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                role.setId(resultSet.getLong(1));
            }
            return role;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to create role: " + role, e);
        }
    }

    @Override
    public Optional<Role> get(Long id) {
        String query = "SELECT * FROM roles WHERE role_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Role role = getRoleFromResultSet(resultSet);
                return Optional.of(role);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to get role with ID = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Role> getAll() {
        String query = "SELECT * FROM roles";
        try (Connection connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Role> roles = new ArrayList<>();
            while (resultSet.next()) {
                Role role = getRoleFromResultSet(resultSet);
                roles.add(role);
            }
            return roles;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable get all roles: ", e);
        }
    }

    @Override
    public Role update(Role role) {
        String query = "UPDATE roles SET role_name = ? WHERE role = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, role.getRoleName().name());
            preparedStatement.setLong(2, role.getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                role.setId(resultSet.getLong("role_id"));
            }
            return role;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to update role: " + role, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRoleFromRolesQuery = "DELETE FROM roles WHERE role_id = ?;";
        String deleteRolesFromUsersQuery = "DELETE FROM sers_roles WHERE role_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();) {
            var deleteProductFromOrdersStatement =
                    connection.prepareStatement(deleteRolesFromUsersQuery);
            deleteProductFromOrdersStatement.setLong(1, id);
            deleteProductFromOrdersStatement.executeUpdate();
            var deleteProductsFromProductsStatement =
                    connection.prepareStatement(deleteRoleFromRolesQuery);
            deleteProductsFromProductsStatement.setLong(1, id);
            return deleteProductsFromProductsStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to delete role with ID = " + id, e);
        }
    }

    private Role getRoleFromResultSet(ResultSet resultSet) throws SQLException {
        Long roleId = resultSet.getLong("role_id");
        String stringRoleName = resultSet.getString("role_name");
        Role.RoleName roleName = Role.RoleName.valueOf(stringRoleName);
        var role = new Role(roleName);
        role.setId(roleId);
        return role;
    }
}
