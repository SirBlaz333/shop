package com.my.service.cart;

import com.my.entity.Cart;
import com.my.entity.cpu.Cpu;

public interface CartAction {
    void doAction(Cart cart, Cpu cpu, int amount);
}
