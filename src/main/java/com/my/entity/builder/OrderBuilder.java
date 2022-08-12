package com.my.entity.builder;

import com.my.entity.Order;
import com.my.entity.OrderStatus;
import com.my.entity.OrderProduct;
import com.my.entity.User;

import java.util.List;

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

    public OrderBuilder withUser(User user) {
        order.setUser(user);
        return this;
    }

    public OrderBuilder withOrderedProducts(List<OrderProduct> orderProducts) {
        order.setOrderedProducts(orderProducts);
        return this;
    }

    public Order build() {
        return order;
    }
}
