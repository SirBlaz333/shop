package com.my.service.product;

import com.my.entity.Cpu;
import com.my.entity.OrderedProducts;
import com.my.entity.ProductFilterFormBean;
import com.my.service.ServiceException;

import java.util.List;
import java.util.Map;

public interface ProductService {
    Cpu getProductById(int id);
    int getProductAmount(Cpu cpu);
    void buyProduct(OrderedProducts orderedProducts) throws ServiceException;
    List<Cpu> getProducts(ProductFilterFormBean bean);
    int getMaxPagesAndSetPageCount(ProductFilterFormBean bean);
}
