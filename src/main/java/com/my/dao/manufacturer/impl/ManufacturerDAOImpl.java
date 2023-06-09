package com.my.dao.manufacturer.impl;

import com.my.dao.DBException;
import com.my.dao.DBManager;
import com.my.dao.manufacturer.ManufacturerDAO;
import com.my.util.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManufacturerDAOImpl implements ManufacturerDAO {
    public static final String GET_MANUFACTURER_BY_ID = "SELECT name FROM manufacturers WHERE id = ?;";
    public static final String GET_MANUFACTURER_ID = "SELECT id FROM manufacturers WHERE name = ?;";
    private static final Logger LOGGER = LoggerFactory.getLogger(ManufacturerDAOImpl.class);
    private final DBManager dbManager;

    public ManufacturerDAOImpl(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public String getManufacturerById(int id) {
        String manufacturerName = null;
        try(Connection connection = dbManager.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(GET_MANUFACTURER_BY_ID);
            preparedStatement.setInt(BEGIN_INDEX, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                manufacturerName = resultSet.getString(BEGIN_INDEX);
            }
        } catch (DBException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Cannot get manufacturer by id");
        }
        return manufacturerName;
    }

    @Override
    public int getManufacturerId(String name) {
        int manufacturerId = -1;
        try(Connection connection = dbManager.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(GET_MANUFACTURER_ID);
            preparedStatement.setString(BEGIN_INDEX, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                manufacturerId = resultSet.getInt(BEGIN_INDEX);
            }
        } catch (DBException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Cannot get manufacturer by id");
        }
        return manufacturerId;
    }
}
