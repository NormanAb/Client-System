package com.benedict.minibank.Controllers;

import com.benedict.minibank.Models.Client;
import com.benedict.minibank.Models.Model;
import com.benedict.minibank.Models.Report;
import com.benedict.minibank.Utilities.AlertUtility;
import com.benedict.minibank.Utilities.DialogueUtility;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import com.benedict.minibank.Views.MenuOptions;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReportController implements Initializable {
    private static final Logger logger = Logger.getLogger(ReportController.class.getName());

    //@FXML
    //public MenuItem delete_btn;
    @FXML
    private Button addReport_btn;
    @FXML
    private TableView<Report> reports_table;
    @FXML
    private TableColumn<Report, LocalDate> colDate;
    @FXML
    private TableColumn<Report, String> colName;
    @FXML
    private TableColumn<Report, Integer> colID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Initializing ReportController");
        initTableColumns(); // Initialize table columns
        loadReportData();   // Load data into the table
        Model.getInstance().getReports();
        setRowFactoryForReportsTable();

    /*    delete_btn.setOnAction(event -> {
            logger.info("Delete button clicked");
            onDeleteReport();
        });
       */ addReport_btn.setOnAction(event -> {
            logger.info("Add Report button clicked");
            onCreateReport();
        });

        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((obs, oldVal, newVal) -> {
            logger.info("Admin selected menu changed from " + oldVal + " to " + newVal);
            if (newVal == MenuOptions.REPORT_LIST) {
                logger.info("REPORT_LIST menu selected; reloading report data");
                loadReportData(); // Reload data when returning to this view
            }
        });
    }

    private void initTableColumns() {
        logger.info("Setting up table columns");
        // Map TableColumns to their corresponding properties
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));

        colDate.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setText(empty ? "" : date.toString());
            }
        });
    }

    private void setRowFactoryForReportsTable() {
        logger.info("Configuring row factory for reports table");
        reports_table.setRowFactory(tv -> {
            TableRow<Report> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Report selectedReport = row.getItem();
                    logger.info("Double-click detected on report: " + selectedReport.getName() +
                            " (ID: " + selectedReport.getId() + ")");
                    editReport(selectedReport);
                }
            });
            return row;
        });
    }

    private void loadReportData() {
        logger.info("Loading report data");
        // Fetch report data from the Model and populate the table
        ObservableList<Report> reports = Model.getInstance().getReports();
        Model.getInstance().loadReports();
        reports_table.setItems(Model.getInstance().getReports());
        logger.info("Report data loaded; total reports: " + reports.size());
    }

   /* private void onDeleteReport() {
        logger.info("Delete report action triggered");
        Add delete logic here and log success or failure as needed
    }*/

    @FXML
    private void onCreateReport() {
        logger.info("Create report action triggered");
        // Add create report logic here and log success or failure as needed
    }

    private void editReport(Report report) {
        logger.info("Editing report: " + report.getName() + " (ID: " + report.getId() + ")");
        // Add editing logic here and log any necessary details
    }
}
