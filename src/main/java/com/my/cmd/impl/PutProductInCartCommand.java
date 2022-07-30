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

public class PutProductInCartCommand implements Command {
    private final CartUtility cartUtility;
    private final ProductService productService;

    public PutProductInCartCommand(ProductService productService) {
        this.productService = productService;
        cartUtility = new CartUtility();
    }

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response, Method method) throws ServletException, IOException {
        Cart cart = cartUtility.getCart(request.getSession());
        int id = Integer.parseInt(request.getParameter("productId"));
        Cpu cpu = productService.getProductById(id);
        int amount = Integer.parseInt(request.getParameter("amount"));
        cart.put(cpu, amount);
    }
}
