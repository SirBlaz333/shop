package com.my.dao.product.sql;

import com.my.entity.ProductFilterFormBean;

public class ProductSQLQueryBuilder {
    private final static String SPACE = " ";
    private StringBuilder query;
    private boolean first;

    public String buildSelectQuery(ProductFilterFormBean bean, int pageSize, int pageCount) {
        query = new StringBuilder();
        query.append("SELECT * FROM products");
        appendFilters(bean);
        appendSorting(bean);
        appendLimit(pageSize, pageCount);
        query.append(";");
        return query.toString();
    }

    public String buildCountQuery(ProductFilterFormBean bean) {
        query = new StringBuilder();
        query.append("SELECT COUNT(id) FROM products");
        appendFilters(bean);
        query.append(";");
        return query.toString();
    }

    private void appendFilters(ProductFilterFormBean bean) {
        first = true;
        if (bean.getName() != null || bean.getManufacturer() != null || bean.getOriginPrice() != -1 || bean.getBoundPrice() != -1) {
            query.append(" WHERE");
            checkAndAppendSQLFilter("name", bean.getName(), "=");
            checkAndAppendSQLFilter("manufacturer", bean.getManufacturer(), "=");
            checkAndAppendSQLFilter("price", bean.getOriginPrice(), ">=");
            checkAndAppendSQLFilter("price", bean.getBoundPrice(), "<=");
        }
    }

    private void appendSorting(ProductFilterFormBean bean) {
        if (bean.getCriteria() != null) {
            query.append(" ORDER BY").append(SPACE).append(bean.getCriteria());
            query.append(" ").append(bean.getOrder().toString());
        }
    }

    private void appendLimit(int pageSize, int pageCount) {
        if (pageCount > 0) {
            int rowCount = (pageCount - 1) * pageSize;
            query.append(" LIMIT").append(SPACE).append(pageSize).append(", ").append(rowCount);
        }
    }

    private void checkAndAppendSQLFilter(String fieldName, Object fieldValue, String sign) {
        if (fieldValue != null) {
            appendSQLFilter(fieldName, fieldValue, sign);
        }
    }

    private void appendSQLFilter(String fieldName, Object fieldValue, String sign) {
        if (!first) {
            query.append(" AND");
        }
        query.append(SPACE).
                append(fieldName).
                append(SPACE).
                append(sign).
                append(SPACE).
                append(fieldValue);
        first = false;
    }
}
