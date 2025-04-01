package com.benedict.ClientManagement.Services.dao;

import com.benedict.ClientManagement.Models.User;
import com.benedict.ClientManagement.Utilities.UserAuthUtils;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Logger;

public class UserDAO {
    private Connection conn;
    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    // Find user by credentials (username and password)
    public User findUserByCredentials(String userName, String password) {
        ResultSet resultSet = null;
        User user = null;
        String sql = "SELECT id, UserName, Password FROM Users WHERE UserName = ?";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                String storedPasswordHash = resultSet.getString("Password");
                if (UserAuthUtils.verifyPassword(password, storedPasswordHash)) {
                    user = new User(resultSet.getInt("id"),resultSet.getString("UserName"));
                }
            }
        } catch (SQLException e) {
            logger.info("Database error");
            e.printStackTrace();
        }
        return user;
    }

    // Count total number of users
    public int countUsers() {
        int userCount = 0;
        String sql = "SELECT COUNT(*) AS user_count FROM Users";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {
            if (resultSet.next()) {
                userCount = resultSet.getInt("user_count");
                logger.info("User count: " + userCount);
            }
        } catch (SQLException e) {
            logger.severe("Error counting users: " + e.getMessage());
        }
        return userCount;
    }

    // Check if a user exists by username
    public boolean isUserExist(String userName) {
        String sql = "SELECT COUNT(*) AS user_count FROM Users WHERE UserName = ?";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("user_count") > 0;
            }
        } catch (SQLException e) {
            logger.severe("Error checking if user exists: " + e.getMessage());
        }
        return false;
    }

    // Create a new user
    public void createUser(String userName, String password, LocalDate date) {
        String sql = "INSERT INTO Users (UserName, Password, Date) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            stmt.setString(2, UserAuthUtils.hashPassword(password));
            stmt.setDate(3, java.sql.Date.valueOf(date));
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Error creating user: " + e.getMessage());
        }
    }
}
