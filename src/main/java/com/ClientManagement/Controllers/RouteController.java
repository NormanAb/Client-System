package com.ClientManagement.Controllers;
import com.ClientManagement.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;

public class RouteController implements Initializable {

    public BorderPane admin_parent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener(( observableValue, oldVal, newVal) -> {
            // Add switch statements
            switch (newVal){
                case AUTHORS -> admin_parent.setCenter(Model.getInstance().getViewFactory().getClientListView());
                case CREATE_CLIENT-> admin_parent.setCenter(Model.getInstance().getViewFactory().getCreateClientView());
                case INCOME -> admin_parent.setCenter(Model.getInstance().getViewFactory().getIncomeView());
                case CREATE_INCOME -> admin_parent.setCenter(Model.getInstance().getViewFactory().getCreateIncomeView());
                case DASHBOARD -> admin_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
                case CLIENT_LIST -> admin_parent.setCenter(Model.getInstance().getViewFactory().getClientListView());
                case REPORT_LIST -> admin_parent.setCenter(Model.getInstance().getViewFactory().getReportListView());
                case SETTINGS -> admin_parent.setCenter(Model.getInstance().getViewFactory().getSettingsView());
                default -> admin_parent.setCenter(Model.getInstance().getViewFactory().getClientsView());
            }
        });
    }
}
