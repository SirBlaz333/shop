package com.my.service.order.impl;

import com.my.dao.order.OrderDAO;
import com.my.entity.Order;
import com.my.service.order.OrderService;

public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO;

    public OrderServiceImpl(OrderDAO orderDAO){
        this.orderDAO = orderDAO;
    }

    @Override
    public void create(Order order) {
        orderDAO.put(order);
    }
}
