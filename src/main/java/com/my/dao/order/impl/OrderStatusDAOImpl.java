package com.my.dao.order.impl;

import com.my.dao.DBException;
import com.my.dao.DBManager;
import com.my.dao.order.OrderStatusDAO;
import com.my.entity.OrderStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderStatusDAOImpl implements OrderStatusDAO {
    private final DBManager dbManager;
    private final Logger logger;

    public OrderStatusDAOImpl(DBManager dbManager) {
        this.dbManager = dbManager;
        this.logger = Logger.getLogger(getClass().getName());
    }

    @Override
    public int getOrderStatusId(OrderStatus orderStatus) {
        int id = 0;
        try(Connection connection = dbManager.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM order_states WHERE state = ?;");
            preparedStatement.setString(BEGIN_INDEX, orderStatus.toString().toLowerCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                id = resultSet.getInt(BEGIN_INDEX);
            }
        } catch (SQLException | DBException e){
            logger.log(Level.SEVERE, "Cannot get order status");
        }
        return id;
    }
}
