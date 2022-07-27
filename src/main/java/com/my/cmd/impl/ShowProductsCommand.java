package com.my.cmd.impl;

import com.my.cmd.Command;
import com.my.cmd.Method;
import com.my.cmd.impl.util.ProductUtility;
import com.my.entity.Cpu;
import com.my.entity.ProductFilterFormBean;
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
        List<Cpu> cpus = productService.getProducts(productFilterFormBean);
        int maxPages = productService.getMaxPages(productFilterFormBean);
        // TODO: 27.07.2022
        // Move parsing ids to different method or send ids from requests
        request.setAttribute(ProductFilterFormBean.PRODUCT_LIST, cpus);
        request.setAttribute(ProductFilterFormBean.MAX_PAGES, maxPages);
        request.getRequestDispatcher("products.jsp").forward(request, response);
    }

}
