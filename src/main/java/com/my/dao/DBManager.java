package com.my.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static final String CLASS_LOAD_ERROR = "Cannot load class for jdbc driver";
    private static final String URL = "jdbc:mysql://localhost:3306/myshop";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    public synchronized Connection getConnection() throws SQLException, DBException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new DBException(CLASS_LOAD_ERROR);
        }
    }
}
