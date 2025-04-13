package com.ClientManagement.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseDriver {
    private Connection conn;
    private static final Logger logger = Logger.getLogger(DatabaseDriver.class.getName());

    public DatabaseDriver() {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:clientManaging.db");
        } catch (SQLException e) {
            logger.severe("Can't connect to DB: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return conn;
    }

}
