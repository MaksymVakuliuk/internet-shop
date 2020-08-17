package com.internet.shop.controllers;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import com.internet.shop.service.RoleService;
import com.internet.shop.service.UserService;
import com.internet.shop.util.HashUtil;
import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("com.internet.shop");
    private static RoleService roleService = (RoleService) INJECTOR.getInstance(RoleService.class);
    private static UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        insertRole();
        insertUsers();
        resp.sendRedirect(req.getContextPath() + "/");
    }

    private void insertRole() {
        Role adminRole = new Role(Role.RoleName.ADMIN);
        roleService.create(adminRole);
        Role userRole = new Role(Role.RoleName.USER);
        roleService.create(userRole);
    }

    private void insertUsers() {
        byte[] salt = HashUtil.getSalt();
        String pass = HashUtil.hashPassword("apass", salt);
        User adminUser = new User("admin", "admin", pass);
        adminUser.setSalt(salt);
        Role roleName = new Role(Role.RoleName.ADMIN);
        adminUser.setRoles(Set.of(roleName));
        userService.create(adminUser);
        salt = HashUtil.getSalt();
        pass = HashUtil.hashPassword("pass", salt);
        User user = new User("user", "user", pass);
        user.setSalt(salt);
        Role userRole = new Role(Role.RoleName.USER);
        user.setRoles(Set.of(userRole));
        userService.create(user);
    }
}
