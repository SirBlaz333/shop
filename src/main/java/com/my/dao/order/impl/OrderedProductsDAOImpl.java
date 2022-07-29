package com.my.dao.order.impl;

import com.my.dao.DBException;
import com.my.dao.DBManager;
import com.my.dao.order.OrderedProductsDAO;
import com.my.entity.Cpu;
import com.my.entity.OrderedProducts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderedProductsDAOImpl implements OrderedProductsDAO {
    public static final String ADD_ORDERED_PRODUCTS = "INSERT INTO orders_products VALUES (?, ?, ?, ?";
    private final DBManager dbManager;
    private final Logger logger;

    public OrderedProductsDAOImpl(DBManager dbManager) {
        this.dbManager = dbManager;
        logger = Logger.getLogger(getClass().getName());
    }

    @Override
    public void put(OrderedProducts orderedProducts, int orderId) {
        try (Connection connection = dbManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_ORDERED_PRODUCTS);
            put(orderedProducts, orderId, preparedStatement);
        } catch (SQLException | DBException e) {
            logger.log(Level.SEVERE, "Cannot put ordered products in database;");
        }
    }

    private void put(OrderedProducts orderedProducts, int orderId, PreparedStatement preparedStatement) throws SQLException {
        Map<Cpu, Integer> map = orderedProducts.getOrderedProducts();
        for (Cpu cpu : map.keySet()) {
            int index = BEGIN_INDEX;
            preparedStatement.setInt(index++, orderId);
            preparedStatement.setInt(index++, cpu.getId());
            preparedStatement.setDouble(index++, cpu.getPrice());
            preparedStatement.setInt(index, map.get(cpu));
            preparedStatement.execute();
        }
    }
}
