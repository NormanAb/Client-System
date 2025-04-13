package com.ClientManagement.Controllers;

import com.ClientManagement.Models.Model;
import com.ClientManagement.Utilities.AlertUtility;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label password_lbl;
    public TextField address_fld;
    public PasswordField password_fld;
    public Button login_btn;
    public Label error_lbl;
    public Hyperlink register_link;

    public void initialize( URL url, ResourceBundle resourceBundle ){
        login_btn.setOnAction(actionEvent -> onLogin());
        register_link.setOnAction(actionEvent -> onRegister());
    }
    //login code
    public void onLogin() {
        Stage stage = (Stage) error_lbl.getScene().getWindow();
        Model.getInstance().checkCredentials(address_fld.getText(), password_fld.getText());
            if (Model.getInstance().getAdminSuccessFlag()) {
                Model.getInstance().getViewFactory().showAdminWindow();
                Model.getInstance().getViewFactory().closeStage(stage);
            }else{
                address_fld.setText("");
                password_fld.setText("");
                AlertUtility.displayError("Incorrect login data");
                error_lbl.setText("Check username or password");
            }
        }

        public void onRegister(){
            Stage stage = (Stage) error_lbl.getScene().getWindow();
            Model.getInstance().getViewFactory().showRegisterWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
        }

}
