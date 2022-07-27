package com.my.cmd.impl.util;

import com.my.entity.ProductFilterFormBean;
import com.my.entity.SortingOrder;

import javax.servlet.http.HttpServletRequest;

public class ProductUtility {
    private static final int DEFAULT_PAGE_SIZE = 8;
    private static final int DEFAULT_PAGE_COUNT = 1;

    public ProductFilterFormBean createFilterFormBean(HttpServletRequest request) {
        ProductFilterFormBean bean = new ProductFilterFormBean();
        setFilters(request, bean);
        setSorting(request, bean);
        setPagination(request, bean);
        return bean;
    }

    private void setFilters(HttpServletRequest request, ProductFilterFormBean bean){
        String name = request.getParameter(ProductFilterFormBean.NAME);
        String[] manufacturers = request.getParameterValues(ProductFilterFormBean.MANUFACTURER);
        String[] memoryTypes = request.getParameterValues(ProductFilterFormBean.MEMORY_TYPE);
        double originPrice = getDoubleField(request, ProductFilterFormBean.ORIGIN_PRICE);
        double boundPrice = getDoubleField(request, ProductFilterFormBean.BOUND_PRICE);
        bean.setMemoryTypes(memoryTypes);
        bean.setName(name);
        bean.setManufacturers(manufacturers);
        bean.setOriginPrice(originPrice);
        bean.setBoundPrice(boundPrice);
    }

    private void setSorting(HttpServletRequest request, ProductFilterFormBean bean){
        String filterCriteria = request.getParameter(ProductFilterFormBean.SORTING_CRITERIA);
        SortingOrder sortingOrder = parseSortingOrder(request.getParameter(ProductFilterFormBean.SORTING_ORDER));
        bean.setFilterCriteria(filterCriteria);
        bean.setOrder(sortingOrder);
    }

    private void setPagination(HttpServletRequest request, ProductFilterFormBean bean){
        int pageSize = getIntField(request, ProductFilterFormBean.PAGE_SIZE);
        int pageCount = getIntField(request, ProductFilterFormBean.PAGE_COUNT);
        if(pageSize == ProductFilterFormBean.INVALID_NUMBER){
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if(pageCount == ProductFilterFormBean.INVALID_NUMBER){
            pageCount = DEFAULT_PAGE_COUNT;
        }
        bean.setPageSize(pageSize);
        bean.setPageCount(pageCount);
    }

    private int getIntField(HttpServletRequest request, String field) {
        if (request.getParameter(field) != null) {
            return Integer.parseInt(request.getParameter(field));
        }
        return ProductFilterFormBean.INVALID_NUMBER;
    }

    private double getDoubleField(HttpServletRequest request, String field) {
        if (request.getParameter(field) != null) {
            return Double.parseDouble(request.getParameter(field));
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
