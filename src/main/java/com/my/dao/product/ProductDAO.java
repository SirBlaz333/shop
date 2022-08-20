package com.my.dao.product;

import com.my.dao.DAO;
import com.my.dao.DBException;
import com.my.entity.cpu.Cpu;
import com.my.entity.order.OrderProduct;
import com.my.entity.ProductFilterFormBean;
import com.my.entity.dto.CpuDTO;

import java.util.List;

public interface ProductDAO extends DAO {
    CpuDTO getProductById(int id);

    void buyProduct(List<OrderProduct> orderProducts) throws DBException;

    int getProductAmount(Cpu cpu);

    List<CpuDTO> getProducts(ProductFilterFormBean bean);

    int getProductCount(ProductFilterFormBean bean);
}
