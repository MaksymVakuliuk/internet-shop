package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;

import java.sql.SQLOutput;
import java.util.List;

public class Application {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

        initializeBd(productService);
        List<Product> products = productService.getAll();
        for (Product product : products) {
            System.out.println(product.toString());
        }
    }

    private static void initializeBd(ProductService productService) {
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
    }
}
