package com.my.dao.product;

import com.my.dao.DAO;
import com.my.entity.CPU;
import com.my.entity.ProductFilterFormBean;
import com.my.entity.dto.CPUDataTransferObject;

import java.util.List;

public interface ProductDAO extends DAO {
    CPUDataTransferObject getProductById(int id);

    void updateProductAmount(CPU cpu, int amount);

    int getProductAmount(CPU cpu);

    List<CPUDataTransferObject> getProducts(ProductFilterFormBean bean, int pageSize, int pageCount);

    int getProductCount(ProductFilterFormBean bean);
}
