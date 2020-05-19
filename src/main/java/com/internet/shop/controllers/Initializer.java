package com.internet.shop.controllers;

import com.internet.shop.lib.Injector;
import com.internet.shop.service.UserService;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Initializer implements ServletContextListener {
    private static final Injector INJECTOR = Injector.getInstance("com.internet.shop");
    private final UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
