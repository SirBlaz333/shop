package com.my.entity;

public class ProductFilterFormBean {
    public static final String NAME = "productName";
    public static final String MANUFACTURER = "manufacturer";
    public static final String ORIGIN_PRICE = "originPrice";
    public static final String BOUND_PRICE = "boundPrice";
    public static final String SORTING_CRITERIA = "sortingCriteria";
    public static final String SORTING_ORDER = "sortingOrder";
    public static final String PAGE_SIZE = "pageSize";
    public static final String PAGE_COUNT = "pageCount";
    public static final String PRODUCT_LIST = "productList";
    public static final String MEMORY_TYPE = "memoryType";
    public static final String MAX_PAGES = "maxPages";
    public static final int INVALID_NUMBER = -1;
    private String name;
    private double originPrice;
    private double boundPrice;
    private String filterCriteria;
    private SortingOrder order;
    private int[] manufacturerIds;
    private int[] memoryTypeIds;
    private int pageSize;
    private int pageCount;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int[] getMemoryTypeIds() {
        return memoryTypeIds;
    }

    public void setMemoryTypeIds(int[] memoryTypeIds) {
        this.memoryTypeIds = memoryTypeIds;
    }

    public int[] getManufacturerIds() {
        return manufacturerIds;
    }

    public void setManufacturerIds(int[] manufacturerIds) {
        this.manufacturerIds = manufacturerIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(double originPrice) {
        this.originPrice = originPrice;
    }

    public double getBoundPrice() {
        return boundPrice;
    }

    public void setBoundPrice(double boundPrice) {
        this.boundPrice = boundPrice;
    }

    public String getFilterCriteria() {
        return filterCriteria;
    }

    public void setFilterCriteria(String filterCriteria) {
        this.filterCriteria = filterCriteria;
    }

    public SortingOrder getOrder() {
        return order;
    }

    public void setOrder(SortingOrder order) {
        this.order = order;
    }
}
