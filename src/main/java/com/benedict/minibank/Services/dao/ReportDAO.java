package com.benedict.minibank.Services.dao;

import com.benedict.minibank.Models.Client;
import com.benedict.minibank.Models.Report;
import com.benedict.minibank.Models.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Logger;

import com.benedict.minibank.Models.Report;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Logger;

public class ReportDAO {
    private Connection conn;
    private static final Logger logger = Logger.getLogger(ReportDAO.class.getName());

    public ReportDAO(Connection conn) {
        this.conn = conn;
    }

    public void create(Report report) {
        String sql = "INSERT INTO Reports (Date, Name) VALUES (?, ?)";
        int userId = Model.getInstance().getLoggedUserId();
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, report.getDate().toString());
            stmt.setString(2, report.getName()); // Store as TEXT (YYYY-MM-DD)
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Object findById(int id) {
        return null; // Implement this if needed
    }

    public void update(Report report) {
        String sql = "UPDATE Reports SET Date = ?, Name = ? WHERE id = ?";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, report.getDate().toString());
            stmt.setString(2, report.getName());
            stmt.setInt(3, report.getId());

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Report updated");
            } else {
                logger.warning("No report found with id: " + report.getId());
            }
        } catch (SQLException e) {
            logger.severe("Error updating report: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Reports WHERE id = ?";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Report with id " + id + " was successfully deleted");
            } else {
                System.out.println("No report was found with id " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting Report with id " + id);
            e.printStackTrace();
        }
    }

    public ObservableList<Report> findAll() {
        ObservableList<Report> reports = FXCollections.observableArrayList();
        String sql = "SELECT Date, Name, id FROM Reports";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                LocalDate date = LocalDate.parse(rs.getString("Date")); // Parse date from TEXT
                String name = rs.getString("Name");
                int id = rs.getInt("id");


                Report report = new Report(id, date, name);
                reports.add(report);

                // Debug: Print to console to verify
                System.out.printf("Fetched Report: %s (%s)%n", name, date);
            }

        } catch (SQLException e) {
            logger.severe("Error fetching reports: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }

        return reports;
    }
}

