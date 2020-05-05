package com.internet.shop.controllers.shopping.carts.user;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.service.ShoppingCartService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetShoppingCartController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("com.internet.shop");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userID = (Long) req.getSession().getAttribute("userID");
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(userID);
        req.setAttribute("shoppingCart", shoppingCart);
        req.getRequestDispatcher("/WEB-INF/views/shoppingCarts/shoppingCart.jsp")
                .forward(req, resp);
    }
}
