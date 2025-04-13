package com.ClientManagement.Utilities;

import com.ClientManagement.Models.Client;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.Optional;

public class DialogueUtility {
    public static Optional<Client> showEditClientDialogue(Client client) {
        Dialog<Client> dialog = new Dialog<>();
        dialog.setTitle("Edit Client");
        dialog.setHeaderText("Edit the chosen client's information");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        TextField name = new TextField(client.getName());
        TextField surname = new TextField(client.getSurname());
        TextField email = new TextField(client.getEmail());
        TextField phone = new TextField(client.getPhone());

        grid.add(new Label("Name:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Surname:"), 0, 1);
        grid.add(surname, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(email, 1, 2);
        grid.add(new Label("Phone:"), 0, 3);
        grid.add(phone, 1, 3);

        ComboBox<String> status_cbox = new ComboBox<>();
        status_cbox.getItems().addAll(
                "ACTIVE",
                "INACTIVE",
                "COMPLETED",
                "CANCELED"
        );
        status_cbox.getSelectionModel().select(client.getStatus());
        grid.add(new Label("Status:"), 0, 4);
        grid.add(status_cbox, 1, 4);

        dialog.getDialogPane().setContent(grid);

        // Disable the Save button if any field is empty.
        Button saveButton = (Button) dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.disableProperty().bind(
                name.textProperty().isEmpty()
                        .or(surname.textProperty().isEmpty())
                        .or(email.textProperty().isEmpty())
                        .or(phone.textProperty().isEmpty())
        );

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                client.setName(name.getText().trim());
                client.setSurname(surname.getText().trim());
                client.setEmail(email.getText().trim());
                client.setPhone(phone.getText().trim());
                client.setStatus(status_cbox.getSelectionModel().getSelectedItem());
            }
            return client;
        });

        return dialog.showAndWait();
    }
}
