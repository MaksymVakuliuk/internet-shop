package com.internet.shop.controllers;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.UserService;
import java.util.Set;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Initializer implements ServletContextListener {
    private static final Injector INJECTOR = Injector.getInstance("com.internet.shop");
    private final UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);
    private final ProductService productService =
            (ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        var product1 = new Product("Apple", 10.0);
        var product2 = new Product("Orange", 15.0);
        var product3 = new Product("Banana", 29.0);
        productService.create(product1);
        productService.create(product2);
        productService.create(product3);

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
