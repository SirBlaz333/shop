package com.my.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static final String CONNECTION_ERROR = "Cannot obtain a connection to database";
    private static final String URL = "jdbc:mysql://localhost:3306/myshop";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    public synchronized Connection getConnection() throws DBException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new DBException(CONNECTION_ERROR);
        }
    }
}
