package com.my.cmd.impl;

import com.my.cmd.Command;
import com.my.cmd.Method;
import com.my.cmd.impl.util.ProductUtility;
import com.my.entity.Cpu;
import com.my.entity.ProductFilterFormBean;
import com.my.service.product.ProductService;
import com.my.web.page.Pages;

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
        int maxPages = productService.getMaxPagesAndSetPageCount(productFilterFormBean);
        List<Cpu> cpus = productService.getProducts(productFilterFormBean);
        request.setAttribute(ProductFilterFormBean.PRODUCT_LIST, cpus);
        request.setAttribute(ProductFilterFormBean.MAX_PAGES, maxPages);
        request.setAttribute(ProductFilterFormBean.PAGE_COUNT, productFilterFormBean.getPageCount());
        request.setAttribute(ProductFilterFormBean.PAGE_SIZE, productFilterFormBean.getPageSize());
        request.getRequestDispatcher(Pages.PRODUCTS).forward(request, response);
    }

}
