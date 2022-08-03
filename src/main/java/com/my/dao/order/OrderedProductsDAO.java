package com.my.dao.order;

import com.my.dao.DAO;
import com.my.entity.OrderedProduct;

import java.util.List;

public interface OrderedProductsDAO extends DAO {
    void put(List<OrderedProduct> orderedProductList, int orderId);
}
