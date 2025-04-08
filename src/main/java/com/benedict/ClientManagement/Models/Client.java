package com.benedict.ClientManagement.Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDate;

public class Client {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty surname;
    private final SimpleStringProperty email;
    private final SimpleStringProperty phone;
    private final SimpleStringProperty status;
    // New property for creation date
    private final SimpleObjectProperty<LocalDate> date;

    // Constructor for database data (with id)
    public Client(int id, String name, String surname, String email, String phone, String status, LocalDate date) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
        this.status = new SimpleStringProperty(status);
        this.date = new SimpleObjectProperty<>(date);
    }

    // Constructor for new clients (without id), defaults creation date to now.
    public Client(String name, String surname, String email, String phone, String status) {
        this(-1, name, surname, email, phone, status, LocalDate.now());
    }

    // Getters and setters for all properties

    public int getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getSurname() { return surname.get(); }
    public String getEmail() { return email.get(); }
    public String getPhone() { return phone.get(); }
    public String getStatus() { return status.get(); }
    public LocalDate getDate() { return date.get(); }

    public void setId(int id) { this.id.set(id); }
    public void setName(String name) { this.name.set(name); }
    public void setSurname(String surname) { this.surname.set(surname); }
    public void setEmail(String email) { this.email.set(email); }
    public void setPhone(String phone) { this.phone.set(phone); }
    public void setStatus(String status) { this.status.set(status); }
    public void setDate(LocalDate date) { this.date.set(date); }

}
