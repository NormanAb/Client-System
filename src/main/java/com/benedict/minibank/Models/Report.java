package com.benedict.minibank.Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDate;

public class Report {
    private final SimpleIntegerProperty id;
    private final SimpleObjectProperty<LocalDate> date;
    private final SimpleStringProperty name;

    public Report(int id, LocalDate date, String name) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.date = new SimpleObjectProperty<>(date);
    }

    public Report(LocalDate date, String name) {
        this(-1, date, name);
    }

    public int getId() { return id.get(); }
    public String getName() { return name.get(); }
    public LocalDate getDate() { return date.get(); }

    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleStringProperty nameProperty() { return name; }
    public SimpleObjectProperty<LocalDate> dateProperty() { return date; }

    public void setId(int id) {
        this.id.set(id);
    }
}
