package com.my.service.cart.impl;

import com.my.entity.Cart;
import com.my.entity.cpu.Cpu;
import com.my.service.cart.CartAction;
import com.my.service.product.ProductService;

public class CartPutAction implements CartAction {
    private final ProductService productService;

    public CartPutAction(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void doAction(Cart cart, Cpu cpu, int amount) {
        if (productService.getProductAmount(cpu) > cart.get(cpu)) {
            cart.put(cpu, amount);
        }
    }
}
