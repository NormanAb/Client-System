package com.benedict.ClientManagement.Views;

import com.benedict.ClientManagement.Controllers.DashboardController;
import com.benedict.ClientManagement.Controllers.RouteController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.logging.Logger;

public class ViewFactory {
    private static final Logger logger = Logger.getLogger(ViewFactory.class.getName());
    private final ObjectProperty<MenuOptions> adminSelectedMenuItem;
    private AnchorPane createClientView;
    private AnchorPane clientsView;
    private AnchorPane depositView;
    private AnchorPane ClientsView;
    private AnchorPane createAuthorView;
    private AnchorPane incomeView;
    private AnchorPane createIncome;
    private AnchorPane dashboardView;
    private AnchorPane clientListView;
    private AnchorPane reportListView;

    private AnchorPane settingsView;

    private DashboardController dashboardController;

    public Pane getDashboardView() {
        if(dashboardView == null){
            try {
                logger.info("Loading Dashboard.fxml for dashboardView.");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Dashboard.fxml"));
                dashboardView = loader.load();
                dashboardController = loader.getController();
                logger.info("dashboardView loaded successfully.");
            } catch (Exception e){
                logger.severe("Error loading dashboardView: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return dashboardView;
    }

    public DashboardController getDashboardController() {
        // Ensure dashboard is loaded
        getDashboardView();
        return dashboardController;
    }

    public ViewFactory(){
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
        logger.info("ViewFactory initialized.");
    }

    // User login
    public void showLoginWindow (){
        logger.info("Showing Login Window.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }

    // User register
    public void showRegisterWindow (){
        logger.info("Showing Register Window.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Register.fxml"));
        createStage(loader);
    }

    public ObjectProperty<MenuOptions> getAdminSelectedMenuItem(){
        return adminSelectedMenuItem;
    }

    public AnchorPane getClientsView() {
        if(ClientsView == null) {
            try {
                logger.info("Loading ClientsList.fxml for ClientsView.");
                ClientsView = new FXMLLoader(getClass().getResource("/Fxml/ClientsList.fxml")).load();
                logger.info("ClientsView loaded successfully.");
            } catch (Exception e){
                logger.severe("Error loading ClientsView: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return ClientsView;
    }

    public AnchorPane getCreateClientView() {
        if(createClientView == null){
            try {
                logger.info("Loading CreateClient.fxml for createClientView.");
                createClientView = new FXMLLoader(getClass().getResource("/Fxml/CreateClient.fxml")).load();
                logger.info("createClientView loaded successfully.");
            } catch (Exception e){
                logger.severe("Error loading createClientView: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return createClientView;
    }

    public AnchorPane getClientListView() {
        if(clientListView == null){
            try {
                logger.info("Loading ClientsList.fxml for clientListView.");
                clientListView = new FXMLLoader(getClass().getResource("/Fxml/ClientsList.fxml")).load();
                logger.info("clientListView loaded successfully.");
            } catch (Exception e){
                logger.severe("Error loading clientListView: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return clientListView;
    }

    public AnchorPane getReportListView() {
        if(reportListView == null){
            try {
                logger.info("Loading Reports.fxml for reportListView.");
                reportListView = new FXMLLoader(getClass().getResource("/Fxml/Reports.fxml")).load();
                logger.info("reportListView loaded successfully.");
            } catch (Exception e){
                logger.severe("Error loading reportListView: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return reportListView;
    }


    public AnchorPane getIncomeView() {
        if(incomeView == null){
            try {
                logger.info("Loading Income.fxml for incomeView.");
                incomeView = new FXMLLoader(getClass().getResource("/Fxml/Income.fxml")).load();
                logger.info("incomeView loaded successfully.");
            } catch (Exception e){
                logger.severe("Error loading incomeView: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return incomeView;
    }

    public AnchorPane getCreateIncomeView() {
        if(createIncome == null){
            try {
                logger.info("Loading AddIncome.fxml for createIncome.");
                createIncome = new FXMLLoader(getClass().getResource("/Fxml/AddIncome.fxml")).load();
                logger.info("createIncome loaded successfully.");
            } catch (Exception e){
                logger.severe("Error loading createIncome: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return createIncome;
    }

    public Pane getSettingsView() {
        if(settingsView == null){
            try {
                logger.info("Loading Settings.fxml for settingsView.");
                settingsView = new FXMLLoader(getClass().getResource("/Fxml/Settings.fxml")).load();
                logger.info("settingsView loaded successfully.");
            } catch (Exception e){
                logger.severe("Error loading settingsView: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return settingsView;
    }




    public void showAdminWindow(){
        logger.info("Showing Admin Window.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Main.fxml"));
        RouteController controller = new RouteController();
        loader.setController(controller);
        createStage(loader);
    }

    public void createStage(FXMLLoader loader){
        Scene scene = null;
        try {
            logger.info("Creating new stage using FXML: " + loader.getLocation());
            scene = new Scene(loader.load());
        } catch(Exception e) {
            logger.severe("Error creating scene: " + e.getMessage());
            e.printStackTrace();
        }
        scene.getStylesheets().add(getClass().getResource("/Styles/Normano.css").toExternalForm());


        Stage stage = new Stage();
        try {
            Image icon = new Image(String.valueOf(getClass().getResource("/Images/icon.png")));
            stage.getIcons().add(icon);
            logger.info("Stage icon set successfully.");
        } catch(Exception e) {
            logger.severe("Error setting stage icon: " + e.getMessage());
            e.printStackTrace();
        }
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Benedikto knygynas");
        stage.show();
        logger.info("Stage shown with title 'Benedikto knygynas'.");
    }

    public void closeStage(Stage stage){
        logger.info("Closing stage with title: " + stage.getTitle());
        stage.close();
    }
}
