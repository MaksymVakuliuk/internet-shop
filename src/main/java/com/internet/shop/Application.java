package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
import com.internet.shop.model.User;
import com.internet.shop.service.OrderService;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;
import java.util.stream.Collectors;

public class Application {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void start() {
        var productService = (ProductService) injector.getInstance(ProductService.class);
        testProductService(productService);

        var userService = (UserService) injector.getInstance(UserService.class);
        testUserService(userService);

        var shoppingCartService = (ShoppingCartService) injector
                .getInstance(ShoppingCartService.class);
        testShoppingCartService(shoppingCartService, userService, productService);

        var orderService = (OrderService) injector.getInstance(OrderService.class);
        testOrderService(orderService, shoppingCartService, userService);
    }

    public static void main(String[] args) {
        start();
    }

    private static void testProductService(ProductService productService) {
        var product1 = new Product("Apple", 10.0);
        var product2 = new Product("Orange", 15.0);
        var product3 = new Product("Banana", 29.0);
        productService.create(product1);
        productService.create(product2);
        productService.create(product3);
        productService.delete(2L);
        productService.create(new Product("PineApple", 45.0));
        var newProduct = new Product("Apple", 12.2);
        newProduct.setId(1L);
        productService.update(newProduct);

        var products = productService.getAll();
        for (Product product : products) {
            System.out.println(product.toString());
        }
    }

    public static void testUserService(UserService userService) {
        var user1 = new User("people1", "people@1", "pass1");
        var user2 = new User("people2", "people@2", "pass2");
        var user3 = new User("people3", "people@3", "pass3");
        userService.create(user1);
        userService.create(user2);
        userService.create(user3);
        userService.delete(2L);
        userService.create(new User("people4", "people@4", "pass4"));
        var newUser = new User("newPeople", "people@newPeople", "newPass");
        newUser.setId(1L);
        userService.update(newUser);

        var users = userService.getAll();
        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    private static void testShoppingCartService(ShoppingCartService shoppingCartService,
                                                UserService userService,
                                                ProductService productService) {
        var users = userService.getAll();
        var products = productService.getAll();

        System.out.println("Add products to user 2");
        shoppingCartService.addProduct(shoppingCartService.getByUserId(users.get(2).getId()),
                products.get(1));
        shoppingCartService.addProduct(shoppingCartService.getByUserId(users.get(2).getId()),
                products.get(2));

        System.out.println("Print products of user 2: ");
        System.out.println(shoppingCartService
                .getAllProducts(shoppingCartService.getByUserId(users.get(2).getId()))
                .stream()
                .map(Product::toString).collect(Collectors.joining(" ")));

        System.out.println("Print products of user 2 after clear: ");
        shoppingCartService.clear(shoppingCartService.getByUserId(users.get(2).getId()));
        System.out.println(shoppingCartService
                .getAllProducts(shoppingCartService.getByUserId(users.get(2).getId()))
                .stream()
                .map(Product::toString).collect(Collectors.joining(" ")));

        System.out.println("Print products of user 2 after fill: ");
        for (Product product : products) {
            shoppingCartService.addProduct(shoppingCartService.getByUserId(users.get(2).getId()),
                    product);
        }
        System.out.println(shoppingCartService
                .getAllProducts(shoppingCartService.getByUserId(users.get(2).getId()))
                .stream()
                .map(Product::toString).collect(Collectors.joining(" ")));

        System.out.println("delete product 3 of user 2: ");
        shoppingCartService
                .deleteProduct(shoppingCartService
                        .getByUserId(users.get(2).getId()), products.get(2));

        System.out.println("Print products of user 2 after remove: ");
        System.out.println(shoppingCartService
                .getAllProducts(shoppingCartService.getByUserId(users.get(2).getId()))
                .stream()
                .map(Product::toString).collect(Collectors.joining(" ")));
    }

    private static void testOrderService(OrderService orderService,
                                         ShoppingCartService shoppingCartService,
                                         UserService userService) {
        orderService.completeOrder(shoppingCartService.getByUserId(4L).getProducts(),
                userService.get(4L));
        System.out.println("All orders : ");
        System.out.println(orderService.getAll()
                .stream()
                .map(Order::toString)
                .collect(Collectors.joining(" ")));

        System.out.println("Get orders by id: ");
        System.out.println(orderService.get(1L));

        System.out.println("Get orders of users: ");
        System.out.println(orderService.getUserOrders(userService.get(4L)));

        orderService.delete(1L);

        System.out.println("All orders after delete: ");
        System.out.println(orderService.getAll()
                .stream()
                .map(Order::toString)
                .collect(Collectors.joining(" ")));
    }
}
