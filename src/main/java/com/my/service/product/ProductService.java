package com.my.service.product;

import com.my.entity.CPU;
import com.my.entity.ProductFilterFormBean;
import com.my.service.ServiceException;

import java.util.List;

public interface ProductService {
    CPU getProductById(int id);
    void buyProduct(CPU cpu, int amount) throws ServiceException;
    void putProduct(CPU cpu, int amount);
    List<CPU> getProducts(ProductFilterFormBean bean);
    int getProductCount(ProductFilterFormBean bean);
}
