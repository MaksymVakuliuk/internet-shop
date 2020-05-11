package com.internet.shop.controllers;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import com.internet.shop.service.UserService;
import java.util.Set;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Initializer implements ServletContextListener {
    private static final Injector INJECTOR = Injector.getInstance("com.internet.shop");
    private final UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        var admin = new User("admin", "admin", "admin");
        admin.setRoles(Set.of(Role.of("ADMIN")));
        userService.create(admin);

        var userTest = new User("1", "1", "1");
        userTest.setRoles(Set.of(Role.of("USER")));
        userService.create(userTest);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
