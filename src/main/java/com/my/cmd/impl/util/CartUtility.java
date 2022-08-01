package com.my.cmd.impl.util;

import com.my.entity.Cart;

import javax.servlet.http.HttpSession;

public class CartUtility {
    public static final String CART = "cart";

    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(CART);
        if (cart == null) {
            cart = createNewCart(session);
        }
        return cart;
    }

    public Cart createNewCart(HttpSession session){
        Cart cart = new Cart();
        session.setAttribute(CART, cart);
        return cart;
    }
}
