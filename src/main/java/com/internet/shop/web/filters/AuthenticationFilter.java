package com.internet.shop.web.filters;

import com.internet.shop.lib.Injector;
import com.internet.shop.service.UserService;
import java.io.IOException;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationFilter implements Filter {
    private static final String USER_ID = "userID";
    private static final Injector INJECTOR = Injector.getInstance("com.internet.shop");
    private static UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private Set<String> urls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        urls = Set.of("/users/registration", "/users/authentication", "/products/all");
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String url = req.getServletPath();
        if (urls.contains(url)) {
            filterChain.doFilter(req, resp);
            return;
        }

        Long userID = (Long) req.getSession().getAttribute(USER_ID);
        if (userID == null || userService.get(userID) == null) {
            resp.sendRedirect("/users/authentication");
            return;
        }
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
