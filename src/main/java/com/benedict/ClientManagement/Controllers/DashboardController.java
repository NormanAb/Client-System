package com.benedict.ClientManagement.Controllers;

import com.benedict.ClientManagement.Models.Client;
import com.benedict.ClientManagement.Models.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class DashboardController implements Initializable {

    private static final Logger logger = Logger.getLogger(DashboardController.class.getName());

    @FXML
    private PieChart clientsPieChart;

    @FXML
    private BarChart<String, Number> newClientBarChart;

    @FXML
    private Label totalClients;

    @FXML
    private Label activeClients;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateClientCounts();
        populatePieChart();
        populateNewClientsBarChart();
    }

    /**
     * Updates the totalClients and activeClients labels.
     */
    private void updateClientCounts() {
        ObservableList<Client> clients = Model.getInstance().getClients();
        int total = clients.size();
        int active = (int) clients.stream()
                .filter(client -> "ACTIVE".equalsIgnoreCase(client.getStatus()))
                .count();
        totalClients.setText(String.valueOf(total));
        activeClients.setText(String.valueOf(active));
        logger.info("Updated client counts: Total = " + total + ", Active = " + active);
    }

    /**
     * Populates the PieChart with counts of clients per status.
     */
    private void populatePieChart() {
        ObservableList<Client> clients = Model.getInstance().getClients();
        Map<String, Integer> statusCounts = new HashMap<>();

        for (Client client : clients) {
            String status = client.getStatus();
            statusCounts.put(status, statusCounts.getOrDefault(status, 0) + 1);
        }

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : statusCounts.entrySet()) {
            pieData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        clientsPieChart.setData(pieData);
        logger.info("Populated PieChart with data: " + pieData);
    }

    /**
     * Populates the BarChart with the number of clients added for each of the last 7 days.
     */
    private void populateNewClientsBarChart() {
        ObservableList<Client> clients = Model.getInstance().getClients();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Clients Added (Last 7 Days)");

        LocalDate today = LocalDate.now();
        Map<String, Integer> dateCounts = new HashMap<>();
        // Initialize counts for the last 7 days
        for (int i = 0; i < 7; i++) {
            LocalDate date = today.minusDays(i);
            dateCounts.put(date.toString(), 0);
        }

        for (Client client : clients) {
            LocalDate creationDate = client.getDate();
            if (creationDate != null && !creationDate.isBefore(today.minusDays(6))) {
                String dateStr = creationDate.toString();
                dateCounts.put(dateStr, dateCounts.getOrDefault(dateStr, 0) + 1);
            }
        }

        // Add data points sorted by date ascending
        dateCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry ->
                        series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()))
                );

        newClientBarChart.getData().clear();
        newClientBarChart.getData().add(series);
        logger.info("Populated BarChart with data: " + series.getData());
    }

    /**
     * Captures a snapshot of the newClientBarChart as a BufferedImage.
     * This image can be used, for example, to embed into a PDF.
     */
    public BufferedImage captureBarChartSnapshot() {
        // Call snapshot() on the instance newClientBarChart
        WritableImage snapshot = newClientBarChart.snapshot(new SnapshotParameters(), null);
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);
        return bufferedImage;
    }

    public BufferedImage capturePieChartSnapshot() {
        WritableImage snapshot = clientsPieChart.snapshot(new SnapshotParameters(), null);
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);
        return bufferedImage;
    }
}
