package com.internet.shop.controllers.orders.admin;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Order;
import com.internet.shop.model.User;
import com.internet.shop.service.OrderService;
import com.internet.shop.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetUserOrdersController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("com.internet.shop");
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String userID = req.getParameter("userID");
        User user = userService.get(Long.valueOf(userID));
        List<Order> orders = orderService.getUserOrders(user);
        req.setAttribute("orders", orders);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/views/orders/admin/userOrders.jsp")
                .forward(req, resp);
    }
}
