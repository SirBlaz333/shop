package com.my.entity;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private final Map<Cpu, Integer> cart;

    public Cart() {
        cart = new HashMap<>();
    }

    public void put(Cpu cpu, int amount) {
        cart.merge(cpu, amount, Integer::sum);
    }

    public void set(Cpu cpu, int amount){
        if(amount == 0){
            cart.remove(cpu);
        } else {
            cart.put(cpu, amount);
        }
    }

    public void remove(Cpu cpu, int amount) {
        Integer oldAmount = cart.get(cpu);
        if (oldAmount != null) {
            amount = oldAmount - amount;
            if(amount == 0){
                remove(cpu);
            } else {
                cart.put(cpu, amount);
            }
        }
    }

    public void remove(Cpu cpu) {
        cart.remove(cpu);
    }

    public int get(Cpu cpu) {
        Integer value = cart.get(cpu);
        if(value == null){
            return 0;
        }
        return value;
    }

    public Map<Cpu, Integer> getMap() {
        return new HashMap<>(cart);
    }

    public int getSize() {
        return cart.size();
    }

    public void clear(){
        cart.clear();
    }

    public double calculatePrice() {
        double totalPrice = 0;
        for (Cpu cpu : cart.keySet()) {
            totalPrice += cpu.getPrice() * get(cpu);
        }
        return totalPrice;
    }
}
