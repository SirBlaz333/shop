package com.my.entity;

public class ProductFilterFormBean {
    private String name;
    private String manufacturer;
    private double originPrice;
    private double boundPrice;
    private String criteria;
    private SortingOrder order;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public SortingOrder getOrder() {
        return order;
    }

    public void setOrder(SortingOrder order) {
        this.order = order;
    }
}
