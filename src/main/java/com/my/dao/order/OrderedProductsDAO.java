package com.my.dao.order;

import com.my.dao.DAO;
import com.my.entity.OrderProduct;

import java.util.List;

public interface OrderedProductsDAO extends DAO {
    void put(List<OrderProduct> orderProductList, int orderId);
}
