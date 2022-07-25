package com.my.service.product.impl;

import com.my.dao.manufacturer.ManufacturerDAO;
import com.my.dao.mt.MemoryTypeDAO;
import com.my.dao.product.ProductDAO;
import com.my.entity.CPU;
import com.my.entity.ProductFilterFormBean;
import com.my.entity.dto.CpuDTO;
import com.my.service.ServiceException;
import com.my.service.product.ProductService;

import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {
    private final ProductDAO productDAO;
    private final ManufacturerDAO manufacturerDAO;
    private final MemoryTypeDAO memoryTypeDAO;

    public ProductServiceImpl(ProductDAO productDAO, ManufacturerDAO manufacturerDAO, MemoryTypeDAO memoryTypeDAO) {
        this.productDAO = productDAO;
        this.manufacturerDAO = manufacturerDAO;
        this.memoryTypeDAO = memoryTypeDAO;
    }

    @Override
    public CPU getProductById(int id) {
        CpuDTO cpuDTO = productDAO.getProductById(id);
        return parseCPUDataTransferObject(cpuDTO);
    }

    @Override
    public void buyProduct(CPU cpu, int amount) throws ServiceException {
        int productAmount = productDAO.getProductAmount(cpu);
        if (productAmount - amount < 0) {
            throw new ServiceException("Cannot buy this amount of product");
        }
        productAmount -= amount;
        productDAO.updateProductAmount(cpu, productAmount);
    }

    @Override
    public void putProduct(CPU cpu, int amount) {
        int productAmount = productDAO.getProductAmount(cpu) + amount;
        productDAO.updateProductAmount(cpu, productAmount);
    }

    @Override
    public List<CPU> getProducts(ProductFilterFormBean bean) {
        bean.setManufacturerId(manufacturerDAO.getManufacturerId(bean.getManufacturer()));
        List<CpuDTO> cpuDTOs = productDAO.getProducts(bean);
        return cpuDTOs.stream()
                .map(this::parseCPUDataTransferObject)
                .collect(Collectors.toList());
    }

    @Override
    public int getProductCount(ProductFilterFormBean bean) {
        return productDAO.getProductCount(bean);
    }

    private CPU parseCPUDataTransferObject(CpuDTO cpuDTO) {
        CPU cpu = cpuDTO.getCpu();
        String memoryType = memoryTypeDAO.getMemoryTypeById(cpuDTO.getMemoryTypeId());
        String manufacturer = manufacturerDAO.getManufacturerById(cpuDTO.getManufacturerId());
        cpu.setManufacturer(manufacturer);
        cpu.setMemoryType(memoryType);
        return cpu;
    }
}
