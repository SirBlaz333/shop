package com.my.service.product;

import com.my.entity.Cpu;
import com.my.entity.order.OrderProduct;
import com.my.entity.ProductFilterFormBean;
import com.my.service.ServiceException;

import java.util.List;

public interface ProductService {
    Cpu getProductById(int id);
    int getProductAmount(Cpu cpu);
    void buyProduct(List<OrderProduct> orderProducts) throws ServiceException;
    List<Cpu> getProducts(ProductFilterFormBean bean);
    int getMaxPagesAndSetPageCount(ProductFilterFormBean bean);
}
