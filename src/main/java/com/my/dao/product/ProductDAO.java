package com.my.dao.product;

import com.my.dao.DAO;
import com.my.entity.Cpu;
import com.my.entity.ProductFilterFormBean;
import com.my.entity.dto.CpuDTO;

import java.util.List;

public interface ProductDAO extends DAO {
    CpuDTO getProductById(int id);

    void updateProductAmount(Cpu cpu, int amount);

    int getProductAmount(Cpu cpu);

    List<CpuDTO> getProducts(ProductFilterFormBean bean);

    int getProductCount(ProductFilterFormBean bean);
}
