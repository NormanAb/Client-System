package com.benedict.ClientManagement;

import com.benedict.ClientManagement.Models.Model;
import com.benedict.ClientManagement.Utilities.AlertUtility;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {


    @Override
    public void start(Stage stage){
        //Model.getInstance().getViewFactory().showLoginWindow();
        if (Model.getInstance().hasRegisteredUsers()){
            Model.getInstance().getViewFactory().showLoginWindow();
        } else {
            AlertUtility.displayInformation("Before doing anything, you must first create a user");
            Model.getInstance().getViewFactory().showRegisterWindow();
        }


    }
}
