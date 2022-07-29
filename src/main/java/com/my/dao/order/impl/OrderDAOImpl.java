package com.my.dao.order.impl;

import com.my.dao.DBException;
import com.my.dao.DBManager;
import com.my.dao.order.OrderDAO;
import com.my.entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAOImpl implements OrderDAO {
    public static final String ADD_ORDER = "INSERT INTO orders (state_id, state_description, datetime, user_id) VALUES (?, ?, ?, ?);";
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
            preparedStatement.setInt(index, order.getUser().getId());
            preparedStatement.execute();
        } catch (SQLException | DBException e) {
            logger.log(Level.SEVERE, "Cannot put order in database");
        }
    }
}
