package com.my.cmd.impl;

import com.my.cmd.Command;
import com.my.cmd.Method;
import com.my.cmd.impl.util.ProductUtility;
import com.my.entity.CPU;
import com.my.entity.ProductFilterFormBean;
import com.my.entity.ProductsFilterFields;
import com.my.service.product.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowProductsCommand implements Command {
    private final ProductService productService;
    private final ProductUtility productUtility;

    public ShowProductsCommand(ProductService productService) {
        this.productService = productService;
        productUtility = new ProductUtility();
    }

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response, Method method) throws ServletException, IOException {
        ProductFilterFormBean productFilterFormBean = productUtility.createFilterFormBean(request);
        int pageSize = productUtility.getIntField(request, ProductsFilterFields.PAGE_SIZE);
        int pageCount = productUtility.getIntField(request, ProductsFilterFields.PAGE_COUNT);
        int productCount = productService.getProductCount(productFilterFormBean);
        List<CPU> cpus = productService.getProducts(productFilterFormBean, pageSize, pageCount);
        request.setAttribute(ProductsFilterFields.PRODUCT_LIST, cpus);
        request.getRequestDispatcher("products.jsp").forward(request, response);
    }


}
