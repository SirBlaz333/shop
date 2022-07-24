package com.my.cmd.impl.util;

import com.my.entity.ProductFilterFormBean;
import com.my.entity.ProductsFilterFields;
import com.my.entity.SortingOrder;

import javax.servlet.http.HttpServletRequest;

public class ProductUtility {

    public int getIntField(HttpServletRequest request, String field) {
        if (request.getParameter(field) != null) {
            return Integer.parseInt(request.getParameter(field));
        }
        return ProductFilterFormBean.INAPPROPRIATE_NUMBER;
    }

    public ProductFilterFormBean createFilterFormBean(HttpServletRequest request) {
        ProductFilterFormBean productFilterFormBean = new ProductFilterFormBean();
        String name = request.getParameter(ProductsFilterFields.NAME);
        String manufacturer = request.getParameter(ProductsFilterFields.MANUFACTURER);
        double originPrice = getPrice(request, ProductsFilterFields.ORIGIN_PRICE);
        double boundPrice = getPrice(request, ProductsFilterFields.BOUND_PRICE);
        String filterCriteria = request.getParameter(ProductsFilterFields.FILTER_CRITERIA);
        SortingOrder sortingOrder = parseSortingOrder(request.getParameter(ProductsFilterFields.SORTING_ORDER));
        productFilterFormBean.setName(name);
        productFilterFormBean.setManufacturer(manufacturer);
        productFilterFormBean.setOriginPrice(originPrice);
        productFilterFormBean.setBoundPrice(boundPrice);
        productFilterFormBean.setFilterCriteria(filterCriteria);
        productFilterFormBean.setOrder(sortingOrder);
        return productFilterFormBean;
    }

    private double getPrice(HttpServletRequest request, String field) {
        if (request.getParameter(field) != null) {
            return Double.parseDouble(request.getParameter(field));
        }
        return ProductFilterFormBean.INAPPROPRIATE_NUMBER;
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
