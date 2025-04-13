package com.ClientManagement.Controllers;

import com.ClientManagement.Models.Model;
import com.ClientManagement.Utilities.AlertUtility;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class LoginController implements Initializable {
    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @FXML
    public Label password_lbl;
    @FXML
    public TextField address_fld;
    @FXML
    public PasswordField password_fld;
    @FXML
    public Button login_btn;
    @FXML
    public Label error_lbl;
    @FXML
    public Hyperlink register_link;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_btn.setOnAction(actionEvent -> onLogin());
        register_link.setOnAction(actionEvent -> onRegister());
    }

    public void onLogin() {
        String username = address_fld.getText().trim();
        String password = password_fld.getText(); // Consider not trimming in case password has spaces

        // If username field is empty, alert and return.
        if (username.isEmpty()) {
            AlertUtility.displayError("Username cannot be empty.");
            error_lbl.setText("Username is required.");
            return;
        }

        // Check if the username exists.
        if (!Model.getInstance().isUserAlreadyRegistered(username)) {
            AlertUtility.displayError("Username not found.");
            error_lbl.setText("Username not found.");
            address_fld.clear();
            password_fld.clear();
            return;
        }

        // Now, check credentials.
        Model.getInstance().checkCredentials(username, password);
        if (!Model.getInstance().getAdminSuccessFlag()) {
            AlertUtility.displayError("Password incorrect.");
            error_lbl.setText("Password incorrect.");
            password_fld.clear();
            return;
        }

        // Successful login: close the current stage and show the admin window.
        Stage stage = (Stage) error_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().showAdminWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
    }

    public void onRegister(){
        Stage stage = (Stage) error_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().showRegisterWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
    }
}
