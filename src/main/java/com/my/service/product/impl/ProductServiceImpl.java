package com.my.service.product.impl;

import com.my.dao.manufacturer.ManufacturerDAO;
import com.my.dao.mt.MemoryTypeDAO;
import com.my.dao.product.ProductDAO;
import com.my.entity.Cpu;
import com.my.entity.ProductFilterFormBean;
import com.my.entity.dto.CpuDTO;
import com.my.service.ServiceException;
import com.my.service.product.ProductService;

import java.util.List;
import java.util.function.Function;
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
    public Cpu getProductById(int id) {
        CpuDTO cpuDTO = productDAO.getProductById(id);
        return parseCPUDataTransferObject(cpuDTO);
    }

    @Override
    public void buyProduct(Cpu cpu, int amount) throws ServiceException {
        int productAmount = productDAO.getProductAmount(cpu);
        if (productAmount - amount < 0) {
            throw new ServiceException("Cannot buy this amount of product");
        }
        productAmount -= amount;
        productDAO.updateProductAmount(cpu, productAmount);
    }

    @Override
    public void putProduct(Cpu cpu, int amount) {
        int productAmount = productDAO.getProductAmount(cpu) + amount;
        productDAO.updateProductAmount(cpu, productAmount);
    }

    @Override
    public List<Cpu> getProducts(ProductFilterFormBean bean) {
        List<CpuDTO> cpuDTOs = productDAO.getProducts(bean);
        return cpuDTOs.stream()
                .map(this::parseCPUDataTransferObject)
                .collect(Collectors.toList());
    }

    private int[] parseArray(String[] array, Function<String, Integer> function) {
        if (array == null) {
            return null;
        }
        int[] numbers = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            numbers[i] = function.apply(array[i]);
        }
        return numbers;
    }

    @Override
    public int getMaxPagesAndSetPageCount(ProductFilterFormBean bean) {
        int productCount = productDAO.getProductCount(bean);
        int maxPages = (int) Math.ceil((double) productCount / bean.getPageSize());
        if(bean.getPageCount() > maxPages){
            bean.setPageCount(1);
        }
        return maxPages;
    }

    private Cpu parseCPUDataTransferObject(CpuDTO cpuDTO) {
        Cpu cpu = cpuDTO.getCpu();
        String memoryType = memoryTypeDAO.getMemoryTypeById(cpuDTO.getMemoryTypeId());
        String manufacturer = manufacturerDAO.getManufacturerById(cpuDTO.getManufacturerId());
        cpu.setManufacturer(manufacturer);
        cpu.setMemoryType(memoryType);
        return cpu;
    }
}
