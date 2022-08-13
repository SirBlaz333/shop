package com.my.dao.user.impl;

import com.my.dao.DBException;
import com.my.dao.DBManager;
import com.my.dao.user.UserRolesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRolesDAOImpl implements UserRolesDAO {
    private final DBManager dbManager;
    private final Logger logger;

    public UserRolesDAOImpl(DBManager dbManager) {
        this.dbManager = dbManager;
        logger = Logger.getLogger(getClass().getName());
    }

    @Override
    public String getUserRoleById(int id) {
        String userRole = null;
        try(Connection connection = dbManager.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM user_roles WHERE id = ?;");
            preparedStatement.setInt(BEGIN_INDEX, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                userRole = resultSet.getString(BEGIN_INDEX);
            }
        } catch (DBException | SQLException e) {
            logger.log(Level.SEVERE, "Cannot get user role by id");
        }
        return userRole;
    }

    @Override
    public int getUserRoleId(String userRole) {
        int userRoleId = -1;
        try(Connection connection = dbManager.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM user_roles WHERE name = ?;");
            preparedStatement.setString(BEGIN_INDEX, userRole);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                userRoleId = resultSet.getInt(BEGIN_INDEX);
            }
        } catch (DBException | SQLException e) {
            logger.log(Level.SEVERE, "Cannot get user role by id");
        }
        return userRoleId;
    }
}
