package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.model.User;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.UserService;
import java.util.List;

public class Application {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        UserService userService = (UserService) injector.getInstance(UserService.class);

        testProductDao(productService);
        testUserDao(userService);
    }

    private static void testProductDao(ProductService productService) {
        Product product1 = new Product("Apple", 10.0);
        Product product2 = new Product("Orange", 15.0);
        Product product3 = new Product("Banana", 29.0);
        productService.create(product1);
        productService.create(product2);
        productService.create(product3);
        productService.delete(2L);
        productService.create(new Product("PineApple", 45.0));
        Product newProduct = new Product("Apple", 12.2);
        newProduct.setId(1L);
        productService.update(newProduct);

        List<Product> products = productService.getAll();
        for (Product product : products) {
            System.out.println(product.toString());
        }
    }

    private static void testUserDao(UserService userService) {
        User user1 = new User("people1", "people@1", "pass1");
        User user2 = new User("people2", "people@2", "pass2");
        User user3 = new User("people3", "people@3", "pass3");
        userService.create(user1);
        userService.create(user2);
        userService.create(user3);
        userService.delete(2L);
        userService.create(new User("people4", "people@4", "pass4"));
        User newUser = new User("newPeople", "people@newPeople", "newPass");
        newUser.setId(1L);
        userService.update(newUser);

        List<User> users = userService.getAll();
        for (User user : users) {
            System.out.println(user.toString());
        }
    }

}
