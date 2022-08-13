package com.my.entity.order;

public class OrderProduct {
    private final int id;
    private final double price;
    private final int quantity;

    public OrderProduct(int id, double price, int quantity) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
