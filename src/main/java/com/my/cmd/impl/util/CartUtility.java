package com.my.cmd.impl.util;

import com.my.entity.Cart;

import javax.servlet.http.HttpSession;

public class CartUtility {
    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
}
