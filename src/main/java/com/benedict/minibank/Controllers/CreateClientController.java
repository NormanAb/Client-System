package com.benedict.minibank.Controllers;
import com.benedict.minibank.Models.Model;
import com.benedict.minibank.Utilities.AlertUtility;
import com.benedict.minibank.Views.MenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
public class CreateClientController implements Initializable {

    public TextField name_fld;
    public TextField surname_fld;
    public TextField email_fld;
    public PasswordField phone_fld;
    public Button create_client_btn;
    public Label error_lbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        create_client_btn.setOnAction(event -> onClient());
    }

    private void onClient() {
        String name = name_fld.getText();
        String surname= surname_fld.getText();
        String email = email_fld.getText();
        String phone = phone_fld.getText();

        Model.getInstance().loadClients();
        Model.getInstance().createClient(name, surname, email, phone);
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(MenuOptions.CLIENT_LIST);
        AlertUtility.displayInformation("Autorius išsaugotas sėkmingai");

    }

    private void emptyFields() {
        name_fld.setText("");
        surname_fld.setText("");
        email_fld.setText("");
        phone_fld.setText("");
    }
}
