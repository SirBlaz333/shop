package com.my.cmd.impl;

import com.my.cmd.Command;
import com.my.cmd.Method;
import com.my.cmd.impl.util.CartUtility;
import com.my.entity.Cart;
import com.my.entity.Cpu;
import com.my.service.cart.CartAction;
import com.my.service.cart.CartActionContainer;
import com.my.service.product.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProcessCartCommand implements Command {
    private final CartUtility cartUtility;
    private final ProductService productService;
    private final CartActionContainer container;

    public ProcessCartCommand(ProductService productService) {
        this.productService = productService;
        cartUtility = new CartUtility();
        container = new CartActionContainer(productService);
    }

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response, Method method) throws ServletException, IOException {
        int id = getInt(request.getParameter(CartUtility.PRODUCT_ID));
        int amount = getInt(request.getParameter(CartUtility.AMOUNT));
        String action = request.getParameter(CartUtility.ACTION);
        Cart cart = cartUtility.getCart(request.getSession());
        Cpu cpu = productService.getProductById(id);
        CartAction cartAction = container.getAction(action);
        cartAction.doAction(cart, cpu, amount);
    }

    private int getInt(String value) {
        if (value == null) {
            return 0;
        }
        return Integer.parseInt(value);
    }
}
