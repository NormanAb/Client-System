package com.benedict.minibank.Services.dao;

import com.benedict.minibank.Models.Client;
import com.benedict.minibank.Models.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Logger;

public class ClientDAO implements GenericDAO{
    private Connection conn;
    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());

    public ClientDAO(Connection conn) {
        this.conn = conn;
    }

    public void create(Client client) {
        String sql = "INSERT INTO Clients (Name, Surname, Email, Phone, Date, User_id) VALUES (?, ?, ?, ?, ?, ?)";
        int userId = Model.getInstance().getLoggedUserId();
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getSurname());
            stmt.setString(3, client.getEmail());
            stmt.setString(4, client.getPhone());
            stmt.setString(5, LocalDate.now().toString()); // Store as TEXT (YYYY-MM-DD)
            stmt.setInt(6, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object findById(int id) {
        return null;
    }

    @Override
    public void create(Object entity) {

    }

    @Override
    public void update(Object entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public ObservableList<Client> findAll() {
        ObservableList<Client> clients = FXCollections.observableArrayList();
        String sql = "SELECT id, Name, Surname, Email, Phone FROM Clients";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("Name");
                String surname = rs.getString("Surname");
                String email = rs.getString("Email");
                String phone = rs.getString("Phone");

                Client client = new Client(id, name, surname, email, phone);
                clients.add(client);

                // Debug: Print to console to verify
                System.out.printf("Fetched Client: %s %s (%s, %s)%n", name, surname, email, phone);
            }

        } catch (SQLException e) {
            logger.severe("Error fetching clients: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }

        return clients;
    }

}
