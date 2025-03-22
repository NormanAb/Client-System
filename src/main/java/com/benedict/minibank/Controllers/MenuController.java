package com.benedict.minibank.Controllers;

import com.benedict.minibank.Models.Model;
import com.benedict.minibank.Views.MenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    public Button logout_btn;
    public Button clients_btn;
    public Button reports_btn;
    public Button profile_btn;
    public Button report_btn;
    public Button dashboard_btn;



    @Override
    public void initialize( URL url, ResourceBundle resourceBundle) {
        dashboard_btn.setOnAction(event -> onDashboard());
        clients_btn.setOnAction(event -> onClientsList());
        reports_btn.setOnAction(event -> onReportsList());
        addListeners();
    }


    private void addListeners(){
        logout_btn.setOnAction(event->onLogout());

    }

    private void onDashboard() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(MenuOptions.DASHBOARD);
    }

    private void onClientsList() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(MenuOptions.CLIENT_LIST);
    }

    private void onReportsList() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(MenuOptions.REPORT_LIST);
    }

    private void onLogout(){
        //Get stage
        Stage stage = (Stage) dashboard_btn.getScene().getWindow();
        //Close client window
        Model.getInstance().getViewFactory().closeStage(stage);
        //Show Login Window
        Model.getInstance().getViewFactory().showLoginWindow();
        //Set flag to false
        Model.getInstance().setClientAdminSuccessFlag(false);

    }
}
