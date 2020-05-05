package com.internet.shop.controllers.orders.admin;

import com.internet.shop.lib.Injector;
import com.internet.shop.service.OrderService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteOrderController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("com.internet.shop");
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderID = req.getParameter("orderID");
        Long userID = orderService.get(Long.valueOf(orderID)).getUser().getId();
        orderService.delete(Long.valueOf(orderID));
        resp.sendRedirect(req.getContextPath() + "/orders/admin/allOrdersOfUser?userID=" + userID);
    }
}
