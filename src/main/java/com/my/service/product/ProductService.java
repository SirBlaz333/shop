package com.my.service.product;

import com.my.entity.Cpu;
import com.my.entity.ProductFilterFormBean;
import com.my.service.ServiceException;

import java.util.List;

public interface ProductService {
    Cpu getProductById(int id);
    void buyProduct(Cpu cpu, int amount) throws ServiceException;
    void putProduct(Cpu cpu, int amount);
    List<Cpu> getProducts(ProductFilterFormBean bean);
    int getMaxPagesAndSetPageCount(ProductFilterFormBean bean);
}
