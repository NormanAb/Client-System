package com.ClientManagement.Controllers;

import com.ClientManagement.Models.ClientStatus;
import com.ClientManagement.Models.Model;
import com.ClientManagement.Utilities.AlertUtility;
import com.ClientManagement.Views.MenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateClientController implements Initializable {

    public TextField name_fld;
    public TextField surname_fld;
    public TextField email_fld;
    public TextField phone_fld;
    public Button create_client_btn;

    // Updated ComboBox to be type-safe (String) for status values
    public ComboBox<String> status_cbox;
    public Label error_lbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        create_client_btn.setOnAction(event -> onClient());

        // Populate the status combo box with allowed statuses
        status_cbox.getItems().addAll(
                ClientStatus.ACTIVE.name(),
                ClientStatus.INACTIVE.name(),
                ClientStatus.COMPLETED.name(),
                ClientStatus.CANCELED.name(),
                ClientStatus.UNKNOWN.name()
        );
        // Set the default status to ACTIVE
        status_cbox.getSelectionModel().select(ClientStatus.ACTIVE.name());
    }

    private void onClient() {
        String name = name_fld.getText().trim();
        String surname = surname_fld.getText().trim();
        String email = email_fld.getText().trim();
        String phone = phone_fld.getText().trim();
        String status = status_cbox.getSelectionModel().getSelectedItem();
        if(status == null || status.isEmpty()) {
            status = ClientStatus.ACTIVE.name();
        }

        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            AlertUtility.displayError("All fields are required to add a client.");
            error_lbl.setText("All fields must be filled out.");
            return;
        }

        // Create a new client using the selected variables.
        Model.getInstance().createClient(name, surname, email, phone, status);

        // Reload the clients in the Model
        Model.getInstance().loadClients();

        // Switch to the client list view
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(MenuOptions.CLIENT_LIST);

        // Now, refresh the dashboard (if it’s still open) so that its counts and charts update immediately.
        // (The ViewFactory method getDashboardController() ensures the dashboard is loaded.)
        Model.getInstance().getViewFactory().getDashboardController().refreshDashboard();

        AlertUtility.displayInformation("Client saved");
        emptyFields();
    }

    private void emptyFields() {
        name_fld.setText("");
        surname_fld.setText("");
        email_fld.setText("");
        phone_fld.setText("");
        status_cbox.getSelectionModel().select(ClientStatus.ACTIVE.name());
    }
}
