package com.benedict.minibank.Controllers;

import com.benedict.minibank.Models.Client;
import com.benedict.minibank.Models.Model;
import com.benedict.minibank.Views.ClientCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientsController  implements Initializable {
    public ListView<Client> clients_listview;

    @Override
    public void initialize( URL url, ResourceBundle resourceBundle) {
        initData();
        clients_listview.setItems(Model.getInstance().getClients());
        clients_listview.setCellFactory(e->new ClientCellFactory());
    }

    private void initData(){
        if(Model.getInstance().getClients().isEmpty()){
            Model.getInstance().setClients();
        }
    }
}
