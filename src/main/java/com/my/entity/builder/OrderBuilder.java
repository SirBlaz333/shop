package com.my.entity.builder;

import com.my.entity.Order;
import com.my.entity.OrderStatus;
import com.my.entity.OrderedProducts;
import com.my.entity.User;

public class OrderBuilder {
    private final Order order;

    public OrderBuilder() {
        order = new Order();
    }

    public OrderBuilder withId(int id) {
        order.setId(id);
        return this;
    }

    public OrderBuilder withOrderStatus(OrderStatus orderStatus) {
        order.setOrderStatus(orderStatus);
        return this;
    }

    public OrderBuilder withDateTime(String dateTime) {
        order.setDateTime(dateTime);
        return this;
    }

    public OrderBuilder withAddress(String address) {
        order.setAddress(address);
        return this;
    }

    public OrderBuilder withZip(String zip) {
        order.setZip(zip);
        return this;
    }

    public OrderBuilder withCity(String city) {
        order.setCity(city);
        return this;
    }

    public OrderBuilder withUser(User user) {
        order.setUser(user);
        return this;
    }

    public OrderBuilder withOrderedProducts(OrderedProducts orderedProducts) {
        order.setOrderedProducts(orderedProducts);
        return this;
    }

    public Order build() {
        return order;
    }
}
