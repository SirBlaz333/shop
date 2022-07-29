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

    public void put(Cpu cpu, int amount) {
        cart.merge(cpu, amount, Integer::sum);
    }

    public void remove(Cpu cpu, int amount) {
        Integer oldAmount = cart.get(cpu);
        if (oldAmount != null) {
            cart.put(cpu, oldAmount - amount);
        }
    }

    public void remove(Cpu cpu) {
        cart.remove(cpu);
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
