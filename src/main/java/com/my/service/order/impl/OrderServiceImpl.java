package com.my.service.order.impl;

import com.my.dao.order.OrderDAO;
import com.my.dao.order.OrderedProductsDAO;
import com.my.entity.Order;
import com.my.service.order.OrderService;

public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO;
    private final OrderedProductsDAO orderedProductsDAO;

    public OrderServiceImpl(OrderDAO orderDAO, OrderedProductsDAO orderedProductsDAO) {
        this.orderDAO = orderDAO;
        this.orderedProductsDAO = orderedProductsDAO;
    }

    @Override
    public void create(Order order) {
        orderDAO.put(order);
        orderedProductsDAO.put(order.getOrderedProducts(), order.getId());
    }
}
