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
        productFilterFormBean.setManufacturer("Intel");
        productFilterFormBean.setOriginPrice(200);
        productFilterFormBean.setBoundPrice(500);
        productFilterFormBean.setCriteria("price");
        productFilterFormBean.setOrder(SortingOrder.ASC);
        ProductSQLQueryBuilder builder = new ProductSQLQueryBuilder();
        String query = builder.buildSelectQuery(productFilterFormBean, 10, 2);
        String expected = "SELECT * FROM products WHERE name = " +
                "Core I-7 AND manufacturer = Intel " +
                "AND price >= 200.0 AND price <= 500.0 ORDER BY price ASC LIMIT 10, 10;";
        assertEquals(query, expected);
    }

    @Test
    public void testOnlySorting(){
        ProductFilterFormBean productFilterFormBean = new ProductFilterFormBean();
        productFilterFormBean.setOriginPrice(-1);
        productFilterFormBean.setBoundPrice(-1);
        productFilterFormBean.setCriteria("price");
        productFilterFormBean.setOrder(SortingOrder.ASC);
        ProductSQLQueryBuilder builder = new ProductSQLQueryBuilder();
        String query = builder.buildSelectQuery(productFilterFormBean, 10, 2);
        String expected = "SELECT * FROM products ORDER BY price ASC LIMIT 10, 10;";
        assertEquals(query, expected);
    }
}