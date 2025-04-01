package com.benedict.ClientManagement.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private int id;
    private  StringProperty username;


    public User(int id, String username) {
        this.id = id;
        this.username = new SimpleStringProperty(username);
    }

    public String usernameProperty() {
        return  username.get();
    }

    public int getId() {
        return id;
    }


}
