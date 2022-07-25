package com.my.dao.product.sql;

import com.my.entity.ProductFilterFormBean;
import com.my.entity.SortingOrder;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductSQLQueryBuilderTest {

    @Test
    public void testAllFields(){
        ProductFilterFormBean productFilterFormBean = new ProductFilterFormBean();
        productFilterFormBean.setName("Core I-7");
        productFilterFormBean.setManufacturerId(1);
        productFilterFormBean.setOriginPrice(200);
        productFilterFormBean.setBoundPrice(500);
        productFilterFormBean.setFilterCriteria("price");
        productFilterFormBean.setOrder(SortingOrder.ASC);
        productFilterFormBean.setPageSize(10);
        productFilterFormBean.setPageCount(2);
        ProductSQLQueryBuilder builder = new ProductSQLQueryBuilder();
        String query = builder.buildSelectQuery(productFilterFormBean);
        String expected = "SELECT * FROM products WHERE name = " +
                "Core I-7 AND manufacturer_id = 1.0 " +
                "AND price >= 200.0 AND price <= 500.0 ORDER BY price ASC LIMIT 10, 10;";
        assertEquals(query, expected);
    }

    @Test
    public void testOnlySorting(){
        ProductFilterFormBean productFilterFormBean = new ProductFilterFormBean();
        productFilterFormBean.setOriginPrice(-1);
        productFilterFormBean.setBoundPrice(-1);
        productFilterFormBean.setManufacturerId(-1);
        productFilterFormBean.setFilterCriteria("price");
        productFilterFormBean.setOrder(SortingOrder.ASC);
        productFilterFormBean.setPageSize(10);
        productFilterFormBean.setPageCount(2);
        ProductSQLQueryBuilder builder = new ProductSQLQueryBuilder();
        String query = builder.buildSelectQuery(productFilterFormBean);
        String expected = "SELECT * FROM products ORDER BY price ASC LIMIT 10, 10;";
        assertEquals(expected, query);
    }
}