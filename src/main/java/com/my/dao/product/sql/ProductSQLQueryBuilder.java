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
        if (bean.getName() != null || bean.getManufacturerId() != ProductFilterFormBean.INVALID_NUMBER
                || bean.getOriginPrice() != ProductFilterFormBean.INVALID_NUMBER
                || bean.getBoundPrice() != ProductFilterFormBean.INVALID_NUMBER
                || bean.getMemoryTypeId() != ProductFilterFormBean.INVALID_NUMBER) {
            query.append(" WHERE");
            checkAndAppendString("name", bean.getName(), "=");
            checkAndAppendNumber("memory_type_id", bean.getMemoryTypeId(), "=");
            checkAndAppendNumber("manufacturer_id", bean.getManufacturerId(), "=");
            checkAndAppendNumber("price", bean.getOriginPrice(), ">=");
            checkAndAppendNumber("price", bean.getBoundPrice(), "<=");
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

    private void checkAndAppendString(String fieldName, String fieldValue, String sign) {
        if (fieldValue != null) {
            appendSQLFilter(fieldName, fieldValue, sign);
        }
    }

    private void checkAndAppendNumber(String fieldName, double number, String sign) {
        if (number != ProductFilterFormBean.INVALID_NUMBER) {
            appendSQLFilter(fieldName, number, sign);
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
