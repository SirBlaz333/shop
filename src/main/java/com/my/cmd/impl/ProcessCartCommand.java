package com.my.cmd.impl;

import com.my.cmd.Command;
import com.my.cmd.Method;
import com.my.cmd.impl.util.CartUtility;
import com.my.entity.Cart;
import com.my.entity.Cpu;
import com.my.service.product.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProcessCartCommand implements Command {
    private final CartUtility cartUtility;
    private final ProductService productService;

    public ProcessCartCommand(ProductService productService) {
        this.productService = productService;
        cartUtility = new CartUtility();
    }

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response, Method method) throws ServletException, IOException {
        Cart cart = cartUtility.getCart(request.getSession());
        int id = getInt(request.getParameter("productId"));
        Cpu cpu = productService.getProductById(id);
        int amount = getInt(request.getParameter("amount"));
        String action = request.getParameter("action");
        doAction(cpu, amount, cart, action);
    }

    private void doAction(Cpu cpu, int amount, Cart cart, String action) {
        if (action.equals("put")) {
            cart.put(cpu, amount);
        }
        if (action.equals("removeAll")) {
            cart.remove(cpu);
        }
        if (action.equals("remove")) {
            cart.remove(cpu, amount);
        }
        if (action.equals("clear")) {
            cart = new Cart();
        }
    }

    private int getInt(String value) {
        if (value == null) {
            return 0;
        }
        return Integer.parseInt(value);
    }
}
