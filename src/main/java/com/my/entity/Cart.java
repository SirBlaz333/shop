package com.my.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private final Map<Cpu, Integer> cart;

    public Cart() {
        cart = new HashMap<>();
    }

    public void put(Cpu cpu) {
        cart.merge(cpu, 1, Integer::sum);
    }

    public void remove(Cpu cpu) {
        Integer amount = cart.get(cpu);
        if (amount != null) {
            cart.put(cpu, amount - 1);
        }
    }

    public int getCpuAmount(Cpu cpu) {
        return cart.get(cpu);
    }

    public List<Cpu> getList() {
        return new ArrayList<>(cart.keySet());
    }

    public int getSize() {
        return cart.size();
    }

    public double calculatePrice() {
        double totalPrice = 0;
        for (Cpu cpu : cart.keySet()) {
            totalPrice += cpu.getPrice() * getCpuAmount(cpu);
        }
        return totalPrice;
    }
}
