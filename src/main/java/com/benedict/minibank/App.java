package com.benedict.minibank;

import com.benedict.minibank.Models.DatabaseDriver;
import com.benedict.minibank.Models.Model;
import com.benedict.minibank.Utilities.AlertUtility;
import com.benedict.minibank.Utilities.UserAuthUtils;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class App extends Application {


    @Override
    public void start( Stage stage){
        //Model.getInstance().getViewFactory().showLoginWindow();
        if (Model.getInstance().hasRegisteredUsers()){
            Model.getInstance().getViewFactory().showLoginWindow();
        } else {
            AlertUtility.displayInformation("Prieš pradedant darbą su sistema turite užregistruoti bent vieną vartotoją");
            Model.getInstance().getViewFactory().showRegisterWindow();
        }


    }
}
