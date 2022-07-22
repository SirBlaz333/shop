package com.my.dao.product.sql;

import com.my.entity.ProductFilterFormBean;

public class ProductSQLQueryBuilder {
    private final static String SPACE = " ";
    private final StringBuilder query;
    private boolean first;

    public ProductSQLQueryBuilder(){
        query = new StringBuilder();
        query.append("SELECT * FROM products");
    }

    public String buildQuery(ProductFilterFormBean bean, int pageSize, int pageCount){
        appendFilters(bean);
        appendSorting(bean);
        appendLimit(pageSize, pageCount);
        query.append(";");
        return query.toString();
    }

    private void appendFilters(ProductFilterFormBean bean){
        first = true;
        if(bean.getName() != null || bean.getManufacturer() != null || bean.getOriginPrice() != -1 || bean.getBoundPrice() != -1){
            query.append(" WHERE");
            checkAndAppendSQLFilter("name", bean.getName(), "=");
            checkAndAppendSQLFilter("manufacturer", bean.getManufacturer(), "=");
            checkAndAppendSQLFilter("price", bean.getOriginPrice(), ">=");
            checkAndAppendSQLFilter("price", bean.getBoundPrice(), "<=");
        }
    }

    private void appendSorting(ProductFilterFormBean bean){
        if(bean.getCriteria() != null){
            query.append(" ORDER BY").append(SPACE).append(bean.getCriteria());
            query.append(" ").append(bean.getOrder().toString());
        }
    }

    private void appendLimit(int pageSize, int pageCount){
        int rowCount = (pageCount - 1) * pageSize;
        query.append(" LIMIT").append(SPACE).append(pageSize).append(", ").append(rowCount);
    }

    private void checkAndAppendSQLFilter(String fieldName, Object fieldValue, String sign){
        if(fieldValue != null){
            appendSQLFilter(fieldName, fieldValue, sign);
        }
    }

    private void appendSQLFilter(String fieldName, Object fieldValue, String sign){
        if(!first){
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
