package com.my.service.order.impl;

import com.my.dao.order.OrderDAO;
import com.my.dao.order.OrderStatusDAO;
import com.my.dao.order.OrderedProductsDAO;
import com.my.entity.Order;
import com.my.service.order.OrderService;

public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO;
    private final OrderedProductsDAO orderedProductsDAO;
    private final OrderStatusDAO orderStatusDAO;

    public OrderServiceImpl(OrderDAO orderDAO, OrderedProductsDAO orderedProductsDAO, OrderStatusDAO orderStatusDAO) {
        this.orderDAO = orderDAO;
        this.orderedProductsDAO = orderedProductsDAO;
        this.orderStatusDAO = orderStatusDAO;
    }

    @Override
    public void create(Order order) {
        int orderStatusId = orderStatusDAO.getOrderStatusId(order.getOrderStatus());
        order.setOrderStatusId(orderStatusId);
        orderDAO.put(order);
        orderedProductsDAO.put(order.getOrderedProducts(), order.getId());
    }
}
