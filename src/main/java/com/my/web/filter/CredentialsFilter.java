package com.my.web.filter;

import com.my.cmd.impl.util.CartUtility;
import com.my.cmd.impl.util.RedirectionUtility;
import com.my.entity.Cart;
import com.my.entity.user.User;
import com.my.entity.user.UserRegFields;
import com.my.web.page.Pages;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CredentialsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CartUtility.CART);
        if (cart == null || cart.getSize() == 0) {
            response.sendRedirect(Pages.CART);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
