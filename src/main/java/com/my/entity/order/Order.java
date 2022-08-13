package com.my.entity.order;

import com.my.entity.user.User;

import java.util.List;

public class Order {
    private int id;
    private OrderStatus orderStatus;
    private int orderStatusId;
    private String statusDescription;
    private String dateTime;
    private String address;
    private User user;
    private List<OrderProduct> orderProducts;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderProduct> getOrderedProducts() {
        return orderProducts;
    }

    public void setOrderedProducts(List<OrderProduct> orderProductList) {
        this.orderProducts = orderProductList;
    }
}
