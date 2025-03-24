package com.benedict.minibank.Controllers;

import com.benedict.minibank.Models.Report;
import com.benedict.minibank.Models.Model;
import com.benedict.minibank.Utilities.AlertUtility;
import com.benedict.minibank.Utilities.PDFGenerator;
import com.benedict.minibank.Views.MenuOptions;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ReportController implements Initializable {
    private static final Logger logger = Logger.getLogger(ReportController.class.getName());

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
        initTableColumns();
        loadReportData();
        setRowFactoryForReportsTable();
        setupReportTableContextMenu();

        // onAction event handler must be annotated with @FXML (or set in code)
        addReport_btn.setOnAction(event -> onCreateReport());

        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((obs, oldVal, newVal) -> {
            if (newVal == MenuOptions.REPORT_LIST) {
                loadReportData();
            }
        });
    }

    private void initTableColumns() {
        logger.info("Setting up table columns");
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
        reports_table.setRowFactory(tv -> {
            TableRow<Report> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
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
        ObservableList<Report> reports = Model.getInstance().getReports();
        Model.getInstance().loadReports();
        reports_table.setItems(Model.getInstance().getReports());
        logger.info("Report data loaded; total reports: " + reports.size());
    }

    @FXML
    private void onCreateReport() {
        // Create a new report (customize as needed)
        Report report = new Report(LocalDate.now(), "Monthly Client Report");
        Model.getInstance().createReport(report.getName(), report.getDate());

        // Optionally, generate a PDF file and save it locally:
        String filePath = PDFGenerator.saveReportPDFToFile(report, "pdfs/report_" + report.getId() + ".pdf");
        if (filePath != null) {
            AlertUtility.displayInformation("Report created. PDF saved to: " + filePath);
        } else {
            AlertUtility.displayError("Error generating PDF for the report.");
        }
    }

    private void editReport(Report report) {
        logger.info("Editing report: " + report.getName() + " (ID: " + report.getId() + ")");
        // Add editing logic if needed
    }

    private void setupReportTableContextMenu() {
        reports_table.setRowFactory(tv -> {
            TableRow<Report> row = new TableRow<>();
            ContextMenu rowMenu = new ContextMenu();
            MenuItem downloadItem = new MenuItem("Download");
            downloadItem.setOnAction(event -> {
                Report selectedReport = row.getItem();
                if (selectedReport != null) {
                    byte[] pdfData = PDFGenerator.generateReportPDF(selectedReport);
                    if (pdfData != null) {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Save Report PDF");
                        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
                        File file = fileChooser.showSaveDialog(reports_table.getScene().getWindow());
                        if (file != null) {
                            try (FileOutputStream fos = new FileOutputStream(file)) {
                                fos.write(pdfData);
                                AlertUtility.displayInformation("PDF downloaded to: " + file.getAbsolutePath());
                            } catch (IOException e) {
                                e.printStackTrace();
                                AlertUtility.displayError("Error saving PDF: " + e.getMessage());
                            }
                        }
                    } else {
                        AlertUtility.displayError("Error generating PDF data.");
                    }
                }
            });
            rowMenu.getItems().add(downloadItem);
            row.contextMenuProperty().bind(
                    javafx.beans.binding.Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(rowMenu)
            );
            return row;
        });
    }
}
