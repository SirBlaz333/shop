package com.my.dao.product;

import com.my.entity.CPU;
import com.my.entity.ProductFilterFormBean;

import java.util.List;

public interface ProductDAO {
    CPU getProductById(int id);
    void buyProduct(CPU cpu, int amount);
    List<CPU> getProducts(ProductFilterFormBean bean, int pageSize, int pageCount);
    int getProductCount(ProductFilterFormBean bean);
}
