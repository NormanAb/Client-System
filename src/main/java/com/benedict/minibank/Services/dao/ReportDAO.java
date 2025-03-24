package com.benedict.minibank.Services.dao;

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

public class ReportDAO {
    private Connection conn;
    private static final Logger logger = Logger.getLogger(ReportDAO.class.getName());

    public ReportDAO(Connection conn) {
        this.conn = conn;
        logger.info("ReportDAO initialized with connection " + conn);
    }

    public void create(Report report) {
        logger.info("Creating report: " + report.getName() + " on " + report.getDate());
        String sql = "INSERT INTO Reports (Date, Name) VALUES (?, ?)";
        int userId = Model.getInstance().getLoggedUserId();
        logger.info("Logged user id: " + userId);
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, report.getDate().toString());
            stmt.setString(2, report.getName());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Report created successfully: " + report.getName());
            } else {
                logger.warning("No rows affected when creating report: " + report.getName());
            }
        } catch (SQLException e) {
            logger.severe("Error creating report: " + e.getMessage());
            e.printStackTrace();
        }
    }



    public void update(Report report) {
        logger.info("Updating report with id: " + report.getId());
        String sql = "UPDATE Reports SET Date = ?, Name = ? WHERE id = ?";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, report.getDate().toString());
            stmt.setString(2, report.getName());
            stmt.setInt(3, report.getId());

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                logger.info("Report updated successfully: id=" + report.getId());
            } else {
                logger.warning("No report found with id: " + report.getId());
            }
        } catch (SQLException e) {
            logger.severe("Error updating report with id " + report.getId() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        logger.info("Deleting report with id: " + id);
        String sql = "DELETE FROM Reports WHERE id = ?";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Report with id " + id + " was successfully deleted");
            } else {
                logger.warning("No report was found with id " + id);
            }
        } catch (SQLException e) {
            logger.severe("Error deleting report with id " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ObservableList<Report> findAll() {
        logger.info("Fetching all reports from the database");
        ObservableList<Report> reports = FXCollections.observableArrayList();
        String sql = "SELECT Date, Name, id FROM Reports";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String dateString = rs.getString("Date");
                if (dateString == null) {
                    logger.warning("Null date encountered for report with id: " + rs.getInt("id") + ". Skipping record.");
                    continue; // Skip this record if the date is null
                }
                LocalDate date = LocalDate.parse(dateString);
                String name = rs.getString("Name");
                int id = rs.getInt("id");

                Report report = new Report(id, date, name);
                reports.add(report);
                logger.fine("Fetched Report: " + name + " (" + date + "), id=" + id);
            }
            logger.info("Total reports fetched: " + reports.size());
        } catch (SQLException e) {
            logger.severe("Error fetching reports: " + e.getMessage());
            e.printStackTrace();
        }

        return reports;
    }

    //PDF Things
    public void updateReportPDF(int reportId, byte[] pdfData) {
        String sql = "UPDATE Reports SET PdfBlob = ? WHERE id = ?";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setBytes(1, pdfData);
            stmt.setInt(2, reportId);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                logger.info("Report PDF updated for report id: " + reportId);
            } else {
                logger.warning("No report found with id: " + reportId);
            }
        } catch (SQLException e) {
            logger.severe("Error updating report PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
