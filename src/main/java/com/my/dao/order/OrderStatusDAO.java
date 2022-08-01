package com.my.dao.order;

import com.my.dao.DAO;
import com.my.entity.OrderStatus;

public interface OrderStatusDAO extends DAO {
    int getOrderStatusId(OrderStatus orderStatus);
}
