package com.benedict.minibank.Utilities;

import com.benedict.minibank.Models.Client;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.Optional;

public class DialogueUtility {
    public static Optional<Client> showEditClientDialogue(Client client) {
        Dialog<Client> dialog = new Dialog<>();
        dialog.setTitle("Redaguoti klienta");
        dialog.setHeaderText("Redaguokite pasirinkto autoriaus duomenis");

        ButtonType saveButtonType = new ButtonType("Issaugoti", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setVgap(10); // gap between rows
        grid.setHgap(10);

        // Fields for client's details
        TextField name = new TextField(client.getName());
        TextField surname = new TextField(client.getSurname());
        TextField email = new TextField(client.getEmail());
        TextField phone = new TextField(client.getPhone());

        grid.add(new Label("Vardas:"), 0, 0);
        grid.add(name, 1, 0);

        grid.add(new Label("Pavarde:"), 0, 1);
        grid.add(surname, 1, 1);

        grid.add(new Label("Pastas:"), 0, 2);
        grid.add(email, 1, 2);

        grid.add(new Label("Telefono nr.:"), 0, 3);
        grid.add(phone, 1, 3);

        // Create and add the ComboBox for client status
        ComboBox<String> status_cbox = new ComboBox<>();
        status_cbox.getItems().addAll(
                "ACTIVE",
                "INACTIVE",
                "COMPLETED",
                "CANCELED"
        );
        // Pre-select the current status from the client object
        status_cbox.getSelectionModel().select(client.getStatus());
        grid.add(new Label("Status:"), 0, 4);
        grid.add(status_cbox, 1, 4);

        dialog.getDialogPane().setContent(grid);

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
