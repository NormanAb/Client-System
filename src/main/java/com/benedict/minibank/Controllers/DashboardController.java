package com.benedict.minibank.Controllers;

import com.benedict.minibank.Models.Client;
import com.benedict.minibank.Models.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

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

    // New labels for displaying client counts
    @FXML
    private Label totalClients;

    @FXML
    private Label activeClients;

    @FXML
    private BarChart<String, Number> newClientBarChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateNewClientsBarChart();
        populatePieChart();
        updateClientCounts();
    }

    private void populatePieChart() {
        ObservableList<Client> clients = Model.getInstance().getClients();
        Map<String, Integer> statusCounts = new HashMap<>();

        // Count clients by status
        for (Client client : clients) {
            String status = client.getStatus();
            statusCounts.put(status, statusCounts.getOrDefault(status, 0) + 1);
        }

        // Convert the map to PieChart.Data objects
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : statusCounts.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        // Set the data into the chart
        clientsPieChart.setData(pieChartData);
        logger.info("Pie chart populated with client status data: " + pieChartData);
    }

    private void populateNewClientsBarChart() {
        // Retrieve the client list from your Model.
        ObservableList<Client> clients = Model.getInstance().getClients();
        // Prepare a series to hold the data.
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Clients Added (Last 7 Days)");

        LocalDate today = LocalDate.now();
        // Create a map to store counts for each day (last 7 days)
        Map<String, Integer> dateCounts = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = today.minusDays(i);
            dateCounts.put(date.toString(), 0);
        }

        // Count clients by their creation date
        for (Client client : clients) {
            // Assuming your Client model now has a getDate() method returning LocalDate
            LocalDate creationDate = client.getDate();
            if (creationDate != null && !creationDate.isBefore(today.minusDays(6))) {
                String dateStr = creationDate.toString();
                dateCounts.put(dateStr, dateCounts.getOrDefault(dateStr, 0) + 1);
            }
        }

        // Add the data points to the series (sorted by date ascending)
        dateCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry ->
                        series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()))
                );

        // Set the series into the BarChart
        newClientBarChart.getData().clear();
        newClientBarChart.getData().add(series);
        logger.info("Bar chart populated with recent client data: " + series.getData());
    }

    /**
     * Updates the totalClients and activeClients labels with current client counts.
     */
    private void updateClientCounts() {
        ObservableList<Client> clients = Model.getInstance().getClients();
        int total = clients.size();
        int active = (int) clients.stream()
                .filter(client -> "ACTIVE".equalsIgnoreCase(client.getStatus()))
                .count();

        totalClients.setText(String.valueOf(total));
        activeClients.setText(String.valueOf(active));

        logger.info("Client counts updated: Total = " + total + ", Active = " + active);
    }
}
