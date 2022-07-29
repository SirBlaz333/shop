package com.my.entity;

public class Order {
    private int id;
    private OrderStatus orderStatus;
    private int orderStatusId;
    private String statusDescription;
    private String dateTime;
    private User user;
    private OrderedProducts orderedProducts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderedProducts getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(OrderedProducts orderedProducts) {
        this.orderedProducts = orderedProducts;
    }
}
