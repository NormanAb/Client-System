package com.benedict.minibank.Controllers;
import com.benedict.minibank.Models.Client;
import com.benedict.minibank.Models.Model;
import com.benedict.minibank.Models.Report;
import com.benedict.minibank.Utilities.AlertUtility;
import com.benedict.minibank.Utilities.DialogueUtility;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import com.benedict.minibank.Views.MenuOptions;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReportController implements Initializable {
    @FXML
    public MenuItem delete_btn;
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
        initTableColumns(); // Initialize table columns
        loadReportData();   // Load data into the table
        Model.getInstance().getReports();
        setRowFactoryForReportsTable();


        delete_btn.setOnAction(event ->onDeleteReport());
        addReport_btn.setOnAction(event -> onCreateReport());

        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((obs, oldVal, newVal) -> {
            if (newVal == MenuOptions.REPORT_LIST) {
                loadReportData(); // Reload data when returning to this view
            }
        });
    }







    private void initTableColumns() {
        // Map TableColumns to their corresponding properties
        colDate.setCellValueFactory(new PropertyValueFactory<>("date")); // Maps to idProperty()
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
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Report selectedReport = row.getItem();
                    editReport(selectedReport);
                }
            });
            return row;
        });
    }


    private void loadReportData() {
        // Fetch client data from the Model and populate the table
        ObservableList<Report> reports = Model.getInstance().getReports();
        Model.getInstance().loadReports();
        reports_table.setItems(Model.getInstance().getReports());
    }

    private void onDeleteReport() {

    }

    @FXML
    private void onCreateReport() {

    }

    private void editReport(Report report) {

    }

}
