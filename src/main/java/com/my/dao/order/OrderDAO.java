package com.my.dao.order;

import com.my.dao.DAO;
import com.my.entity.Order;

public interface OrderDAO extends DAO {
    void put(Order order);
}
