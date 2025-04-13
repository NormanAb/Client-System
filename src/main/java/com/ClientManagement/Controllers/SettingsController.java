package com.ClientManagement.Controllers;

import com.ClientManagement.Utilities.AlertUtility;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Scene;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class SettingsController implements Initializable {
    private static final Logger logger = Logger.getLogger(SettingsController.class.getName());

    // Full path to sqlite3 executable – make sure sqlite3.exe exists at this location
    private static final String SQLITE3_EXECUTABLE = "C:\\Users\\Normanas\\Desktop\\sqlite3\\sqlite3.exe";

    // Theme stylesheet paths (ensure these files exist in your resources/Styles folder)
    private static final String DEFAULT_THEME = "/Styles/default.css";
    private static final String WHITE_THEME = "/Styles/white.css";
    private static final String GRAY_THEME = "/Styles/gray.css";
    private static final String DARK_THEME = "/Styles/dark.css";

    @FXML
    private Button createBackup_btn;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Initializing SettingsController");


        // Set up the backup creation event using a FileChooser.
        createBackup_btn.setOnAction(event -> {
            logger.info("Create Backup button clicked");
            // Get the current stage from the button
            Stage stage = (Stage) createBackup_btn.getScene().getWindow();
            // Create a FileChooser for saving the backup file
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save SQL Backup");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SQL Backup Files", "*.sql"));
            File outputFile = fileChooser.showSaveDialog(stage);
            if (outputFile != null) {
                // Use the selected file's absolute path as the output path
                createSQLDump("database.db", outputFile.getAbsolutePath());
            } else {
                logger.info("No file was selected for the backup.");
            }
        });


    }

    /**
     * Updates the stylesheet for every open Stage.
     *
     * @param themeCss The relative path to the CSS file (e.g. "/Styles/dark.css")
     */
    private void updateTheme(String themeCss) {
        for (Window window : Window.getWindows()) {
            if (window instanceof Stage) {
                Stage stage = (Stage) window;
                Scene scene = stage.getScene();
                if (scene != null) {
                    scene.getStylesheets().clear();
                    scene.getStylesheets().add(getClass().getResource(themeCss).toExternalForm());
                    logger.info("Applied theme: " + themeCss + " to stage: " + stage.getTitle());
                }
            }
        }
    }

    /**
     * Creates an SQL dump of the specified SQLite database file and writes it to outputPath.
     * Uses the sqlite3 command-line tool.
     *
     * @param dbPath     The path to the database file.
     * @param outputPath The output file path for the SQL dump.
     */
    private void createSQLDump(String dbPath, String outputPath) {
        try {
            ProcessBuilder pb = new ProcessBuilder(SQLITE3_EXECUTABLE, dbPath, ".dump");
            Process process = pb.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                }
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                logger.info("SQL backup created successfully: " + outputPath);
                // Show an alert with the absolute path of the backup file.
                AlertUtility.displayInformation("SQL backup created successfully and stored at: " +
                        new File(outputPath).getAbsolutePath());
            } else {
                logger.severe("Error creating SQL dump. Exit code: " + exitCode);
                AlertUtility.displayError("Error creating SQL dump. Exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            logger.severe("Exception creating SQL dump: " + e.getMessage());
            e.printStackTrace();
            AlertUtility.displayError("Exception creating SQL dump: " + e.getMessage());
        }
    }
}
