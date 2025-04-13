package com.ClientManagement.Services.dao;

import com.ClientManagement.Models.Client;
import com.ClientManagement.Models.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Logger;

public class ClientDAO {
    private Connection conn;
    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());

    public ClientDAO(Connection conn) {
        this.conn = conn;
    }

    public void create(Client client) {
        String sql = "INSERT INTO Clients (Name, Surname, Email, Phone, Date, Status) VALUES (?, ?, ?, ?, ?, ?)";
        int userId = Model.getInstance().getLoggedUserId();
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getSurname());
            stmt.setString(3, client.getEmail());
            stmt.setString(4, client.getPhone());
            // Use the client's local date
            stmt.setString(5, client.getDate().toString());
            stmt.setString(6, client.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void update(Client client) {
        String sql = "UPDATE Clients SET Name = ?, Surname = ?, Email = ?, Phone = ?, Status = ? WHERE id = ?";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getSurname());
            stmt.setString(3, client.getEmail());
            stmt.setString(4, client.getPhone());
            stmt.setString(5, client.getStatus());  // Update status
            stmt.setInt(6, client.getId());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Client updated");
            } else {
                logger.warning("No client found with id: " + client.getId());
            }
        } catch (SQLException e) {
            logger.severe("Error updating client: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void delete(int id) {
        String sql = "DELETE FROM Clients WHERE id = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Client with id" + id + " was successfully deleted");
            } else {
                System.out.println("No client was found with id " + id );
            }
        }
        catch (SQLException e) {
            System.out.println("Error deleting Client with id " + id);
            e.printStackTrace();
        }
    }

    //handles data retrieval from database
    public ObservableList<Client> findAll() {
        ObservableList<Client> clients = FXCollections.observableArrayList();
        String sql = "SELECT id, Name, Surname, Email, Phone, Status, Date FROM Clients";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("Name");
                String surname = rs.getString("Surname");
                String email = rs.getString("Email");
                String phone = rs.getString("Phone");
                String status = rs.getString("Status");
                String dateString = rs.getString("Date");
                LocalDate date = LocalDate.parse(dateString);

                // Create a new Client including the date
                Client client = new Client(id, name, surname, email, phone, status, date);
                clients.add(client);
                System.out.printf("Fetched Client: %s %s (%s, %s) Status: %s Date: %s%n", name, surname, email, phone, status, date);
            }
        } catch (SQLException e) {
            logger.severe("Error fetching clients: " + e.getMessage());
            e.printStackTrace();
        }

        return clients;
    }

}
