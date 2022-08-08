package com.my.dao.order.impl;

import com.my.dao.DBException;
import com.my.dao.DBManager;
import com.my.dao.order.OrderedProductsDAO;
import com.my.entity.OrderProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderedProductsDAOImpl implements OrderedProductsDAO {
    public static final String ADD_ORDERED_PRODUCTS = "INSERT INTO orders_products VALUES (?, ?, ?, ?);";
    private final DBManager dbManager;
    private final Logger logger;

    public OrderedProductsDAOImpl(DBManager dbManager) {
        this.dbManager = dbManager;
        logger = Logger.getLogger(getClass().getName());
    }

    @Override
    public void put(List<OrderProduct> orderProducts, int orderId) {
        try (Connection connection = dbManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_ORDERED_PRODUCTS);
            put(orderProducts, orderId, preparedStatement);
        } catch (SQLException | DBException e) {
            logger.log(Level.SEVERE, "Cannot put ordered products in database;");
        }
    }

    private void put(List<OrderProduct> orderProducts, int orderId, PreparedStatement preparedStatement) throws SQLException {
        for (OrderProduct orderProduct : orderProducts) {
            int index = BEGIN_INDEX;
            preparedStatement.setInt(index++, orderId);
            preparedStatement.setInt(index++, orderProduct.getId());
            preparedStatement.setDouble(index++, orderProduct.getPrice());
            preparedStatement.setInt(index, orderProduct.getQuantity());
            preparedStatement.execute();
        }
    }
}
