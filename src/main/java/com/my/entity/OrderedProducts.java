package com.my.entity;

import java.util.HashMap;
import java.util.Map;

public class OrderedProducts {
    private final Map<Cpu, Integer> orderedProducts;

    public OrderedProducts(Map<Cpu, Integer> map){
        orderedProducts = new HashMap<>();
        orderedProducts.putAll(map);
    }

    public Map<Cpu, Integer> getOrderedProducts() {
        return new HashMap<>(orderedProducts);
    }
}
