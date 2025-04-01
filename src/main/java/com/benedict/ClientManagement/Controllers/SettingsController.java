package com.benedict.ClientManagement.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class SettingsController implements Initializable {
    private static final Logger logger = Logger.getLogger(SettingsController.class.getName());

    @FXML
    private Button createBackup_btn;

    @FXML
    private Button loadBackup_btn;

    @FXML
    private ComboBox<String> colorPicker_cbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Initializing SettingsController");

        // Populate the color picker with example color options.
        colorPicker_cbox.getItems().addAll("White", "Gray", "Dark");
        logger.info("Color picker initialized with default values");

        // Set up event for create backup button.
        createBackup_btn.setOnAction(event -> {
            logger.info("Create Backup button clicked");
            // TODO: Implement backup creation logic here.
        });

        // Set up event for load backup button.
        loadBackup_btn.setOnAction(event -> {
            logger.info("Load Backup button clicked");
            // TODO: Implement backup loading logic here.
        });

        // Set up event for when a color is selected from the combo box.
        colorPicker_cbox.setOnAction(event -> {
            String selectedColor = colorPicker_cbox.getSelectionModel().getSelectedItem();
            logger.info("Color selected: " + selectedColor);
            // TODO: Implement logic to change the background or handle the selected color.
        });
    }
}
