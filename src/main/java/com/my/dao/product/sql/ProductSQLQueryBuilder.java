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
        if (bean.getName() != null || bean.getManufacturerIds() != null || bean.getMemoryTypeIds() != null
                || bean.getOriginPrice() != ProductFilterFormBean.INVALID_NUMBER
                || bean.getBoundPrice() != ProductFilterFormBean.INVALID_NUMBER) {
            query.append(" WHERE");
            checkAndAppendName(bean.getName());
            appendArrayFilter("memory_type_id", bean.getMemoryTypeIds());
            appendArrayFilter("manufacturer_id", bean.getManufacturerIds());
            checkAndAppendNumber(bean.getOriginPrice(), ">=");
            checkAndAppendNumber(bean.getBoundPrice(), "<=");
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

    private void checkAndAppendName(String name){
        if(name != null){
            appendFilter("`name`", "'" + name + "'", "=");
        }
    }

    private void checkAndAppendNumber(double number, String sign) {
        if (number != ProductFilterFormBean.INVALID_NUMBER) {
            appendFilter("price", number, sign);
        }
    }

    private void appendFilter(String fieldName, Object fieldValue, String sign) {
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

    private void appendArrayFilter(String fieldName, int[] fieldValues){
        if(fieldValues != null){
            if (!first) {
                query.append(" AND");
            }
            query.append(" (");
            for(int i = 0; i < fieldValues.length; i++){
                if(i > 0){
                    query.append(" OR");
                }
                query.append(SPACE).
                        append(fieldName).
                        append(SPACE).
                        append("=").
                        append(SPACE).
                        append(fieldValues[i]);
            }
            query.append(")");
        }
    }
}
