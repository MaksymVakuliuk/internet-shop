package com.internet.shop.web.filters;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import com.internet.shop.service.UserService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationFilter implements Filter {
    private static final Injector INJECTOR = Injector.getInstance("com.internet.shop");
    private static UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private Map<String, Set<Role.RoleName>> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/products/admin/productManagement", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/admin/add", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/admin/delete", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/users/admin/all", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/users/admin/deleteUser", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/orders/admin/userOrders", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/shoppingCarts/addToShoppingCart", Set.of(Role.RoleName.USER));
        protectedUrls.put("/shoppingCarts/shoppingCart", Set.of(Role.RoleName.USER));
        protectedUrls.put("/shoppingCarts/removeProduct", Set.of(Role.RoleName.USER));
        protectedUrls.put("/orders/all", Set.of(Role.RoleName.USER));
        protectedUrls.put("/orders/create", Set.of(Role.RoleName.USER));
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String requestUrl = req.getServletPath();

        if (protectedUrls.get(requestUrl) == null) {
            filterChain.doFilter(req, resp);
            return;
        }

        User user = userService.get((Long) req.getSession().getAttribute("userId"));
        if (isAuthorized(user, protectedUrls.get(requestUrl))) {
            filterChain.doFilter(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/users/accessDenied.jsp")
                    .forward(req, resp);
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isAuthorized(User user, Set<Role.RoleName> authorizedRoles) {
        return authorizedRoles
                .stream()
                .anyMatch(role -> user.getRoles()
                        .stream()
                        .anyMatch(userRoles -> userRoles
                                .getRoleName()
                                .equals(role)));
    }

}
