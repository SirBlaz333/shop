package com.my.dao.mt.impl;

import com.my.dao.DBException;
import com.my.dao.DBManager;
import com.my.dao.mt.MemoryTypeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemoryTypeDAOImpl implements MemoryTypeDAO {
    public static final String GET_MEMORY_TYPE_BY_ID = "SELECT memory_type FROM memory_types WHERE id = ?;";
    public static final String GET_MEMORY_TYPE_ID = "SELECT id FROM memory_types WHERE memory_type = ?;";
    private final DBManager dbManager;
    private final Logger logger;

    public MemoryTypeDAOImpl(DBManager dbManager) {
        this.dbManager = dbManager;
        logger = Logger.getLogger(getClass().getName());
    }

    @Override
    public String getMemoryTypeById(int id) {
        String memoryType = null;
        try (Connection connection = dbManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_MEMORY_TYPE_BY_ID);
            preparedStatement.setInt(BEGIN_INDEX, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                memoryType = resultSet.getString(BEGIN_INDEX);
            }
        } catch (SQLException | DBException e) {
            logger.log(Level.SEVERE, "Cannot get memory type by id");
        }
        return memoryType;
    }

    @Override
    public int getMemoryTypeId(String memoryType) {
        int memoryTypeId = -1;
        try (Connection connection = dbManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_MEMORY_TYPE_ID);
            preparedStatement.setString(BEGIN_INDEX, memoryType);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                memoryTypeId = resultSet.getInt(BEGIN_INDEX);
            }
        } catch (SQLException | DBException e) {
            logger.log(Level.SEVERE, "Cannot get memory type by id");
        }
        return memoryTypeId;
    }
}
