package com.my.dao.product;

import com.my.dao.DAO;
import com.my.entity.CPU;
import com.my.entity.ProductFilterFormBean;
import com.my.entity.dto.CpuDTO;

import java.util.List;

public interface ProductDAO extends DAO {
    CpuDTO getProductById(int id);

    void updateProductAmount(CPU cpu, int amount);

    int getProductAmount(CPU cpu);

    List<CpuDTO> getProducts(ProductFilterFormBean bean);

    int getProductCount(ProductFilterFormBean bean);
}
