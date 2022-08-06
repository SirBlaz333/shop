package com.my.dao.product.sql;

import com.my.entity.ProductFilterFormBean;
import com.my.entity.SortingOrder;
import org.junit.Test;

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
        String expected = "SELECT * FROM products WHERE amount != 0 AND `name` LIKE 'Core I-7%' " +
                "AND (memory_type_id = 5 OR memory_type_id = 6) " +
                "AND (manufacturer_id = 1 OR manufacturer_id = 2 OR manufacturer_id = 3) " +
                "AND price >= 200.0 AND price <= 500.0 ORDER BY price ASC LIMIT 10, 10;";
        assertEquals(query, expected);
    }

    @Test
    public void testOnlySorting(){
        ProductFilterFormBean bean = new ProductFilterFormBean();
        bean.setOriginPrice(-1);
        bean.setBoundPrice(-1);
        bean.setFilterCriteria("price");
        bean.setOrder(SortingOrder.ASC);
        ProductSQLQueryBuilder builder = new ProductSQLQueryBuilder();
        String query = builder.buildSelectQuery(bean);
        String expected = "SELECT * FROM products WHERE amount != 0 ORDER BY price ASC;";
        assertEquals(expected, query);
    }

    @Test
    public void testOnlyFilters(){
        ProductFilterFormBean bean = new ProductFilterFormBean();
        bean.setOriginPrice(200);
        bean.setBoundPrice(500);
        bean.setManufacturerIds(new int[]{1, 2});
        bean.setMemoryTypeIds(new int[]{1, 2});
        ProductSQLQueryBuilder builder = new ProductSQLQueryBuilder();
        String query = builder.buildSelectQuery(bean);
        String expected = "SELECT * FROM products WHERE amount != 0 AND " +
                "(memory_type_id = 1 OR memory_type_id = 2) AND (manufacturer_id = 1 " +
                "OR manufacturer_id = 2) AND price >= 200.0 AND price <= 500.0;";
        assertEquals(expected, query);
    }

    @Test
    public void testOnlyLimit(){
        ProductFilterFormBean bean = new ProductFilterFormBean();
        bean.setOriginPrice(-1);
        bean.setBoundPrice(-1);
        bean.setPageSize(10);
        bean.setPageCount(2);
        ProductSQLQueryBuilder builder = new ProductSQLQueryBuilder();
        String query = builder.buildSelectQuery(bean);
        String expected = "SELECT * FROM products WHERE amount != 0 LIMIT 10, 10;";
        assertEquals(expected, query);
    }

    @Test
    public void testNoFilters(){
        ProductFilterFormBean bean = new ProductFilterFormBean();
        bean.setOriginPrice(-1);
        bean.setBoundPrice(-1);
        ProductSQLQueryBuilder builder = new ProductSQLQueryBuilder();
        String query = builder.buildSelectQuery(bean);
        String expected = "SELECT * FROM products WHERE amount != 0;";
        assertEquals(expected, query);
    }

    @Test
    public void testOnlyMemoryType(){
        ProductFilterFormBean bean = new ProductFilterFormBean();
        bean.setOriginPrice(-1);
        bean.setBoundPrice(-1);
        bean.setMemoryTypeIds(new int[]{1, 2});
        ProductSQLQueryBuilder builder = new ProductSQLQueryBuilder();
        String query = builder.buildSelectQuery(bean);
        String expected = "SELECT * FROM products WHERE amount != 0 AND (memory_type_id = 1 OR memory_type_id = 2);";
        assertEquals(expected, query);
    }
}