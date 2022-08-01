package com.my.dao.order.impl;

import com.my.dao.DBException;
import com.my.dao.DBManager;
import com.my.dao.order.OrderDAO;
import com.my.entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAOImpl implements OrderDAO {
    public static final String ADD_ORDER = "INSERT INTO orders (state_id, state_description, datetime, address, user_id) VALUES (?, ?, ?, ?, ?);";
    private final DBManager dbManager;
    private final Logger logger;

    public OrderDAOImpl(DBManager dbManager) {
        this.dbManager = dbManager;
        logger = Logger.getLogger(getClass().getName());
    }

    @Override
    public void put(Order order) {
        try (Connection connection = dbManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_ORDER);
            int index = BEGIN_INDEX;
            preparedStatement.setInt(index++, order.getOrderStatusId());
            preparedStatement.setString(index++, order.getStatusDescription());
            preparedStatement.setString(index++, order.getDateTime());
            preparedStatement.setString(index++, order.getAddress());
            preparedStatement.setInt(index, order.getUser().getId());
            preparedStatement.execute();
            setId(connection, order);
        } catch (SQLException | DBException e) {
            logger.log(Level.SEVERE, "Cannot put order in database");
        }
    }

    private void setId(Connection connection, Order order) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT LAST_INSERT_ID() FROM orders;");
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            order.setId(resultSet.getInt(BEGIN_INDEX));
        }
    }
}
