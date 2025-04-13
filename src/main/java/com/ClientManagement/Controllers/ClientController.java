package com.ClientManagement.Controllers;

import com.ClientManagement.Models.Client;
import com.ClientManagement.Models.Model;
import com.ClientManagement.Utilities.AlertUtility;
import com.ClientManagement.Utilities.DialogueUtility;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import com.ClientManagement.Views.MenuOptions;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientController implements Initializable {
    @FXML
    public MenuItem delete_btn;
    @FXML
    private Button addClient_btn;
    @FXML
    private TableView<Client> clients_table;
    @FXML
    private TableColumn<Client, Integer> colId;
    @FXML
    private TableColumn<Client, String> colName;
    @FXML
    private TableColumn<Client, String> colSurname;
    @FXML
    private TableColumn<Client, String> colEmail;
    @FXML
    private TableColumn<Client, String> colPhone;
    @FXML
    private TableColumn<Client, String> colStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableColumns();
        loadClientData();
        Model.getInstance().getClients();
        setRowFactoryForClientsTable();

        delete_btn.setOnAction(event -> onDeleteClient());
        addClient_btn.setOnAction(event -> onCreateClient());

        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((obs, oldVal, newVal) -> {
            if (newVal == MenuOptions.CLIENT_LIST) {
                loadClientData();
            }
        });
    }

    private void loadClientData() {
        ObservableList<Client> clients = Model.getInstance().getClients();
        Model.getInstance().loadClients();
        clients_table.setItems(Model.getInstance().getClients());
    }

    @FXML
    private void onCreateClient() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(MenuOptions.CREATE_CLIENT);
    }

    private void initTableColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void setRowFactoryForClientsTable() {
        clients_table.setRowFactory(tv -> {
            TableRow<Client> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Client selectedClient = row.getItem();
                    editClient(selectedClient);
                }
            });
            return row;
        });
    }

    private void editClient(Client client) {
        Optional<Client> result = DialogueUtility.showEditClientDialogue(client);
        result.ifPresent(updatedClient -> {
            Model.getInstance().updateClient(client);
            clients_table.refresh(); // Force table refresh after edit.
            // Optionally, update the dashboard if visible:
            Model.getInstance().getViewFactory().getDashboardController().refreshDashboard();
            System.out.println("Update result");
        });
    }

    private void onDeleteClient() {
        Client selectedClient = clients_table.getSelectionModel().getSelectedItem();
        if (selectedClient == null) {
            AlertUtility.displayError("Error selecting client");
            return;
        }

        boolean confirmed = AlertUtility.displayConfirmation("Are you sure you want to delete client?");
        if (confirmed) {
            Model.getInstance().deleteClient(selectedClient.getId());
            ObservableList<Client> clients = clients_table.getItems();
            clients.remove(selectedClient);
            AlertUtility.displayInformation("Client has been removed successfully");
            // Update the dashboard after deletion.
            Model.getInstance().getViewFactory().getDashboardController().refreshDashboard();
        }
    }
}
