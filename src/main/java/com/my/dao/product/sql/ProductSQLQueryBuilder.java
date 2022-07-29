package com.my.dao.product.sql;

import com.my.entity.ProductFilterFormBean;

public class ProductSQLQueryBuilder {
    private final static String SPACE = " ";
    private StringBuilder query;
    private boolean first;

    public String buildSelectQuery(ProductFilterFormBean bean) {
        query = new StringBuilder();
        query.append("SELECT * FROM products");
        appendFilters(bean);
        appendSorting(bean);
        appendLimit(bean);
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
        query.append(" WHERE");
        appendName(bean.getName());
        appendArray("memory_type_id", bean.getMemoryTypeIds());
        appendArray("manufacturer_id", bean.getManufacturerIds());
        appendPrice(bean.getOriginPrice(), ">=");
        appendPrice(bean.getBoundPrice(), "<=");
        if (first) {
            query.delete(query.length() - 6, query.length());
        }
    }

    private void appendSorting(ProductFilterFormBean bean) {
        if (bean.getFilterCriteria() != null) {
            query.append(" ORDER BY").append(SPACE).append(bean.getFilterCriteria());
            query.append(" ").append(bean.getOrder().toString());
        }
    }

    private void appendLimit(ProductFilterFormBean bean) {
        if (bean.getPageCount() > 0) {
            int rowCount = (bean.getPageCount() - 1) * bean.getPageSize();
            query.append(" LIMIT").append(SPACE).append(rowCount).append(", ").append(bean.getPageSize());
        }
    }

    private void appendName(String name) {
        if (name != null) {
            appendFilter("`name`", "'" + name + "%'", "LIKE");
        }
    }

    private void appendPrice(double number, String sign) {
        if (number != ProductFilterFormBean.INVALID_NUMBER) {
            appendFilter("price", number, sign);
        }
    }

    private void appendFilter(String fieldName, Object fieldValue, String sign) {
        appendAND();
        query.append(SPACE);
        appendParameter(fieldName, fieldValue, sign);
    }

    private void appendArray(String fieldName, int[] fieldValues) {
        if (fieldValues != null) {
            appendAND();
            appendArrayFilters(fieldName, fieldValues);
        }
    }

    private void appendArrayFilters(String fieldName, int[] fieldValues) {
        query.append(" (");
        for (int i = 0; i < fieldValues.length; i++) {
            appendOR(i);
            appendParameter(fieldName, fieldValues[i], "=");
        }
        query.append(")");
    }

    private void appendParameter(String fieldName, Object fieldValue, String sign) {
        query.append(fieldName).
                append(SPACE).
                append(sign).
                append(SPACE).
                append(fieldValue);
        first = false;
    }

    private void appendAND() {
        if (!first) {
            query.append(" AND");
        }
    }

    private void appendOR(int parameterNumber) {
        if (parameterNumber > 0) {
            query.append(" OR ");
        }
    }
}
