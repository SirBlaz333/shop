package com.my.dao.manufacturer;

import com.my.dao.DBException;
import com.my.dao.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManufacturerDAOImpl implements ManufacturerDAO {
    public static final String GET_MANUFACTURER_BY_ID = "SELECT name FROM manufacturers WHERE id = ?;";
    private final Logger logger;
    private final DBManager dbManager;

    public ManufacturerDAOImpl(DBManager dbManager) {
        this.dbManager = dbManager;
        logger = Logger.getLogger(getClass().getName());
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
            logger.log(Level.SEVERE, "Cannot get manufacturer by id");
        }
        return manufacturerName;
    }
}
