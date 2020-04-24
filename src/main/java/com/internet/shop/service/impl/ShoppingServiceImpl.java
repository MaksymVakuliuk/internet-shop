package com.internet.shop.service.impl;

import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.db.Storage;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class ShoppingServiceImpl implements ShoppingCartService {
    @Inject
    private ShoppingCartDao shoppingCartDao;
    @Inject
    private UserService userService;

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        shoppingCart.getProducts().add(product);
        return shoppingCartDao.update(shoppingCart);
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        if (shoppingCart.getProducts()
                .removeIf(p -> p.getId().equals(product.getId()))) {
            shoppingCartDao.update(shoppingCart);
            return true;
        }
        return false;
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        IntStream.range(0, shoppingCart.getProducts().size())
                .forEach(i -> shoppingCart.getProducts()
                        .remove(shoppingCart.getProducts().size() - 1));
        shoppingCartDao.update(shoppingCart);
    }

    @Override
    public ShoppingCart getByUserId(Long userId) {
        return shoppingCartDao.getAll()
                .stream()
                .filter(shoppingCart -> shoppingCart.getUser().getId().equals(userId))
                .findFirst()
                .orElse(Storage.addShoppingCart(new ShoppingCart(userService.get(userId))));
    }

    @Override
    public List<Product> getAllProducts(ShoppingCart shoppingCart) {
        return shoppingCart.getProducts();
    }
    
}
