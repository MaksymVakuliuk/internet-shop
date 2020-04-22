package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.service.ItemService;

public class Application {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ItemService itemService = (ItemService) injector.getInstance(ItemService.class);
    }
}
