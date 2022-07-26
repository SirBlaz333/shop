package com.my.dao.product.sql;

import com.my.entity.ProductFilterFormBean;
import com.my.entity.SortingOrder;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ProductSQLQueryBuilderTest {

    @Test
    public void testAllFields(){
        ProductFilterFormBean bean = new ProductFilterFormBean();
        bean.setName("Core I-7");
        bean.setManufacturerIds(new int[]{1, 2, 3});
        bean.setMemoryTypeIds(new int[]{5, 6});
        bean.setOriginPrice(200);
        bean.setBoundPrice(500);
        bean.setFilterCriteria("price");
        bean.setOrder(SortingOrder.ASC);
        bean.setPageSize(10);
        bean.setPageCount(2);
        ProductSQLQueryBuilder builder = new ProductSQLQueryBuilder();
        String query = builder.buildSelectQuery(bean);
        String expected = "SELECT * FROM products WHERE name = Core I-7 " +
                "AND ( memory_type_id = 5 OR memory_type_id = 6) " +
                "AND ( manufacturer_id = 1 OR manufacturer_id = 2 OR manufacturer_id = 3) " +
                "AND price >= 200.0 AND price <= 500.0 ORDER BY price ASC LIMIT 10, 10;";
        assertEquals(query, expected);
    }

    @Test
    public void testOnlySorting(){
        ProductFilterFormBean productFilterFormBean = new ProductFilterFormBean();
        productFilterFormBean.setOriginPrice(-1);
        productFilterFormBean.setBoundPrice(-1);
        productFilterFormBean.setManufacturerIds(new int[]{1, 2});
        productFilterFormBean.setFilterCriteria("price");
        productFilterFormBean.setOrder(SortingOrder.ASC);
        productFilterFormBean.setPageSize(10);
        productFilterFormBean.setPageCount(2);
        ProductSQLQueryBuilder builder = new ProductSQLQueryBuilder();
        String query = builder.buildSelectQuery(productFilterFormBean);
        String expected = "SELECT * FROM products WHERE ( manufacturer_id = 1 " +
                "OR manufacturer_id = 2) ORDER BY price ASC LIMIT 10, 10;";
        assertEquals(expected, query);
    }
}