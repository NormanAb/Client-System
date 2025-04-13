package com.ClientManagement.Controllers;

import com.ClientManagement.Models.Model;
import com.ClientManagement.Utilities.AlertUtility;
import com.ClientManagement.Utilities.UserAuthUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class RegisterController implements Initializable {
    private static final Logger logger = Logger.getLogger(RegisterController.class.getName());

    @FXML
    public TextField user_name_fld;
    @FXML
    public PasswordField password_fld;
    @FXML
    public PasswordField repeat_password_fld;
    @FXML
    public Button register_btn;
    @FXML
    public Label error_lbl;
    @FXML
    public Hyperlink login_link;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        register_btn.setOnAction(actionEvent -> onRegister());
        login_link.setOnAction(actionEvent -> onLogin());
    }

    public void onRegister() {
        Stage stage = (Stage) error_lbl.getScene().getWindow();
        String username = user_name_fld.getText().trim();
        String password = password_fld.getText();
        String repeatPassword = repeat_password_fld.getText();

        // Check that none of the fields are empty.
        if(username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            AlertUtility.displayError("All fields must be filled out.");
            error_lbl.setText("Please fill all fields.");
            return;
        }

        // If username already exists, alert error.
        if (Model.getInstance().isUserAlreadyRegistered(username)) {
            AlertUtility.displayError("Username already exists. Please choose another.");
            error_lbl.setText("Username already taken.");
            emptyFields();
            return;
        }

        // Check that both password fields match.
        if (!UserAuthUtils.doPasswordsMatch(password, repeatPassword)) {
            AlertUtility.displayError("Passwords do not match.");
            error_lbl.setText("Passwords do not match.");
            return;
        }

        // If everything is valid, create the user.
        Model.getInstance().createUser(username, password);
        AlertUtility.displayInformation("User successfully registered!");
        Model.getInstance().getViewFactory().showLoginWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
    }

    public void onLogin() {
        Stage stage = (Stage) error_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().showLoginWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
    }

    public void emptyFields() {
        user_name_fld.clear();
        password_fld.clear();
        repeat_password_fld.clear();
    }
}
