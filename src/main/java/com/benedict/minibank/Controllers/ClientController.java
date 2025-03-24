package com.benedict.minibank.Controllers;

import com.benedict.minibank.Models.Client;
import com.benedict.minibank.Models.Model;
import com.benedict.minibank.Utilities.AlertUtility;
import com.benedict.minibank.Utilities.DialogueUtility;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import com.benedict.minibank.Views.MenuOptions;
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
        initTableColumns(); // Initialize table columns
        loadClientData();   // Load data into the table
        Model.getInstance().getClients();
        setRowFactoryForClientsTable();


        delete_btn.setOnAction(event ->onDeleteClient());
        addClient_btn.setOnAction(event -> onCreateClient());

        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((obs, oldVal, newVal) -> {
            if (newVal == MenuOptions.CLIENT_LIST) {
                loadClientData(); // Reload data when returning to this view
            }
        });
    }

    private void loadClientData() {
        // Fetch client data from the Model and populate the table
        ObservableList<Client> clients = Model.getInstance().getClients();
        Model.getInstance().loadClients();
        clients_table.setItems(Model.getInstance().getClients());
    }

    @FXML
    private void onCreateClient() {
        // Change the menu to the create author screen
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(MenuOptions.CREATE_CLIENT);
    }

    @FXML
    private void clients_table(SortEvent<TableView<?>> event) {
        // Custom sorting logic here
        System.out.println("Sorting triggered!");
    }


    private void initTableColumns() {
        // Map TableColumns to their corresponding properties
        colId.setCellValueFactory(new PropertyValueFactory<>("id")); // Maps to idProperty()
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

    private void editClient(Client client){
        Optional<Client> result = DialogueUtility.showEditClientDialogue(client);
        result.ifPresent(updateClient -> {
            Model.getInstance().updateClient(client);
            System.out.println("Update result");
        });
    }

    private void onDeleteClient() {
        Client selectedClient = (Client) clients_table.getSelectionModel().getSelectedItem();
        if (selectedClient == null) {
            AlertUtility.displayError("Error selecting client");
        }

        boolean confirmed = AlertUtility.displayConfirmation(
                "Are you sure you want to delete author?"
        );

        if (confirmed) {
            Model.getInstance().deleteClient(selectedClient.getId());
            ObservableList<Client> clients = clients_table.getItems();
            clients.remove(selectedClient);
            AlertUtility.displayInformation("Client has been removed successfully");
        }
    }
}
