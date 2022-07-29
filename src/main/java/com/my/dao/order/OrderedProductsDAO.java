package com.my.dao.order;

import com.my.dao.DAO;
import com.my.entity.OrderedProducts;

public interface OrderedProductsDAO extends DAO {
    void put(OrderedProducts orderedProducts, int orderId);
}
