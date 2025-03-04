package com.benedict.minibank.Models;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Client {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty surname;
    private final SimpleStringProperty email;
    private final SimpleStringProperty phone;

    // Constructor for database data (with id)
    public Client(int id, String name, String surname, String email, String phone) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
    }

    // Constructor for new clients (without id)
    public Client(String name, String surname, String email, String phone) {
        this(-1, name, surname, email, phone);
    }

    // Getters for JavaFX properties
    public int getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getSurname() { return surname.get(); }
    public String getEmail() { return email.get(); }
    public String getPhone() { return phone.get(); }

    // Property getters (required for TableView)
    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleStringProperty nameProperty() { return name; }
    public SimpleStringProperty surnameProperty() { return surname; }
    public SimpleStringProperty emailProperty() { return email; }
    public SimpleStringProperty phoneProperty() { return phone; }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }
}