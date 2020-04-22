package com.internet.shop.service.impl;

import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.Product;
import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.dao.ProductDao;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    private ShoppingCartDao shoppingCartDao;
    @Inject
    private ProductDao productDao;

    @Override
    public ShoppingCart addItem(Long bucketId, Long itemId) {
        ShoppingCart shoppingCart = shoppingCartDao.get(bucketId);
        Product product = productDao.get(itemId).get();
        shoppingCart.getProducts().add(product);
        return shoppingCartDao.update(shoppingCart);
    }
}
