package com.internet.shop.controllers;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import com.internet.shop.service.UserService;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("com.internet.shop");
    private static UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        clear();
        insertUsers();
        resp.sendRedirect(req.getContextPath() + "/");
    }

    private void clear() {
        List<User> allUsers = userService.getAll();
        for (User user : allUsers) {
            userService.delete(user.getId());
        }
    }

    private void insertUsers() {
        User adminUser = new User("admin", "admin", "apass");
        Role roleName = new Role(Role.RoleName.ADMIN);
        adminUser.setRoles(Set.of(roleName));
        userService.create(adminUser);
        User user = new User("user", "user", "pass");
        Role userRole = new Role(Role.RoleName.USER);
        user.setRoles(Set.of(userRole));
        userService.create(user);
    }
}
