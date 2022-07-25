package com.my.cmd.impl.util;

import com.my.entity.ProductFilterFormBean;
import com.my.entity.SortingOrder;

import javax.servlet.http.HttpServletRequest;

public class ProductUtility {

    public ProductFilterFormBean createFilterFormBean(HttpServletRequest request) {
        ProductFilterFormBean productFilterFormBean = new ProductFilterFormBean();
        String name = request.getParameter(ProductFilterFormBean.NAME);
        String manufacturer = request.getParameter(ProductFilterFormBean.MANUFACTURER);
        String memoryType = request.getParameter(ProductFilterFormBean.MEMORY_TYPE);
        double originPrice = getDoubleField(request, ProductFilterFormBean.ORIGIN_PRICE);
        double boundPrice = getDoubleField(request, ProductFilterFormBean.BOUND_PRICE);
        String filterCriteria = request.getParameter(ProductFilterFormBean.FILTER_CRITERIA);
        SortingOrder sortingOrder = parseSortingOrder(request.getParameter(ProductFilterFormBean.SORTING_ORDER));
        int pageSize = getIntField(request, ProductFilterFormBean.PAGE_SIZE);
        int pageCount = getIntField(request, ProductFilterFormBean.PAGE_COUNT);
        productFilterFormBean.setName(name);
        productFilterFormBean.setManufacturer(manufacturer);
        productFilterFormBean.setOriginPrice(originPrice);
        productFilterFormBean.setBoundPrice(boundPrice);
        productFilterFormBean.setFilterCriteria(filterCriteria);
        productFilterFormBean.setOrder(sortingOrder);
        productFilterFormBean.setMemoryType(memoryType);
        productFilterFormBean.setPageSize(pageSize);
        productFilterFormBean.setPageCount(pageCount);
        return productFilterFormBean;
    }

    private double getDoubleField(HttpServletRequest request, String field) {
        if (request.getParameter(field) != null) {
            return Double.parseDouble(request.getParameter(field));
        }
        return ProductFilterFormBean.INVALID_NUMBER;
    }

    private int getIntField(HttpServletRequest request, String field) {
        if (request.getParameter(field) != null) {
            return Integer.parseInt(request.getParameter(field));
        }
        return ProductFilterFormBean.INVALID_NUMBER;
    }

    private SortingOrder parseSortingOrder(String sortingOrder) {
        for (SortingOrder order : SortingOrder.values()) {
            if (order.toString().equals(sortingOrder)) {
                return order;
            }
        }
        return null;
    }
}
