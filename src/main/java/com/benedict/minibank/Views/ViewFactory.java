package com.benedict.minibank.Views;

import com.benedict.minibank.Controllers.RouteController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewFactory {
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


    public ViewFactory(){
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
    }

  //Auth

    /*
    * User login
     */

    public void showLoginWindow (){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }

    /*
     * User register
     */

    public void showRegisterWindow (){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Register.fxml"));
        createStage(loader);
    }

    public ObjectProperty<MenuOptions> getAdminSelectedMenuItem(){
        return adminSelectedMenuItem;
    }

    public AnchorPane getClientsView() {
        if(ClientsView == null){
            try {
                ClientsView = new FXMLLoader(getClass().getResource("/Fxml/ClientsList.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return ClientsView;
    }

    public AnchorPane getCreateClientView() {
        if(createClientView == null){
            try {
                createClientView = new FXMLLoader(getClass().getResource("/Fxml/CreateClient.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return createClientView;
    }

    public AnchorPane getClientListView() {
        if(clientListView == null){
            try {
                clientListView = new FXMLLoader(getClass().getResource("/Fxml/ClientsList.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return clientListView;
    }

    public Pane getDashboardView() {
        if(dashboardView == null){
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/Fxml/Dashboard.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return dashboardView;
    }

    public AnchorPane getIncomeView() {
        if(incomeView == null){
            try {
                incomeView = new FXMLLoader(getClass().getResource("/Fxml/Income.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return incomeView;
    }

    public AnchorPane getCreateIncomeView() {
        if(createIncome == null){
            try {
                createIncome = new FXMLLoader(getClass().getResource("/Fxml/AddIncome.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return createIncome;
    }


    public void showAdminWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Main.fxml"));
        RouteController controller = new RouteController();
        loader.setController(controller);
        createStage(loader);
    }

    public void createStage(FXMLLoader loader){
        Scene scene = null;
        try{
            scene = new Scene(loader.load());
        }catch(Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/icon.png"))));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Benedikto knygynas");
        stage.show();
    }

    public void closeStage(Stage stage){
        stage.close();
    }
}
