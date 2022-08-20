package com.my.service.cart.impl;

import com.my.entity.Cart;
import com.my.entity.cpu.Cpu;
import com.my.service.cart.CartAction;

public class CartRemoveAllAction implements CartAction {
    @Override
    public void doAction(Cart cart, Cpu cpu, int amount) {
        cart.remove(cpu);
    }
}
