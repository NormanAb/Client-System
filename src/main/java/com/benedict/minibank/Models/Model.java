package com.benedict.minibank.Models;

import com.benedict.minibank.Services.dao.ClientDAO;
import com.benedict.minibank.Services.dao.ReportDAO;
import com.benedict.minibank.Services.dao.UserDAO;
import com.benedict.minibank.Views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.time.LocalDate;
import java.util.Optional;

public class Model {

    private static Model model;
    private final ViewFactory viewFactory;
    public final UserDAO userDAO;
    public final ClientDAO authorDAO;

    public final ReportDAO reportDAO;
    public final ClientDAO clientDAO;
    private boolean loginSuccessFlag;

    private final ObservableList<Client> clients;
    private final ObservableList<Report> reports;
    private  User currentUser;



    private Model(){
        this.viewFactory = new ViewFactory();
        this.userDAO = new UserDAO(new DatabaseDriver().getConnection());
        this.authorDAO = new ClientDAO(new DatabaseDriver().getConnection());
        this.clientDAO = new ClientDAO(new DatabaseDriver().getConnection());
        this.reportDAO = new ReportDAO(new DatabaseDriver().getConnection());
        this.loginSuccessFlag = false;
        this.currentUser = null;
        this.clients = FXCollections.observableArrayList();
        this.reports = FXCollections.observableArrayList();
    }

    public static synchronized Model getInstance(){
        if(model == null){
            model = new Model();
        }
        return  model;
    }

    public ViewFactory getViewFactory(){
        return viewFactory;
    }


    public boolean getAdminSuccessFlag(){
        return this.loginSuccessFlag;
    }

    public void  setClientAdminSuccessFlag(boolean flag){
        this.loginSuccessFlag = flag;
    }

    public boolean hasRegisteredUsers() {
        return userDAO.countUsers() > 0;
    }

    public boolean isUserAlreadyRegistered(String userName) {
        return userDAO.isUserExist(userName);
    }

    public void createUser(String userName, String password) {
        userDAO.createUser(userName, password, LocalDate.now());
    }

    public void checkCredentials(String userName, String password){
        User user = userDAO.findUserByCredentials(userName, password);
        if (user != null) {
            this.loginSuccessFlag = true;
            this.currentUser = user;
        }
    }

    public String getLoggedUserName(){
        return  currentUser != null ? currentUser.usernameProperty() : null;
    }

    public int getLoggedUserId(){
        return currentUser != null ? currentUser.getId() : null;
    }

    public void deleteClient(int id) {
        clientDAO.delete(id);
    }
    public void createClient(String name, String surname, String email, String phone){
        Client client = new Client(name, surname, email, phone);

        clientDAO.create(client);
        loadClients();
    }

    public void updateClient(Client client) {
        clientDAO.update(client);
    }

    //Authors
    public ObservableList<Client> getClients(){
        System.out.println(clientDAO.findAll());
        return  clientDAO.findAll();
    }

    public void loadClients() {
        this.clients.clear(); // Clear the existing list
        this.clients.addAll(clientDAO.findAll()); // Repopulate with fresh data from the database
    }


    public ObservableList<Report> getReports() {
        System.out.println(reportDAO.findAll());
        return reportDAO.findAll();
    }

    public void loadReports() {
        reports.clear(); // Clear the existing list
        reports.addAll(reportDAO.findAll()); // Repopulate with fresh data from the database
    }

    public void createReport(String name, LocalDate date) {
        Report report = new Report(-1, date, name); // ID will be assigned by the database
        reportDAO.create(report);
        loadReports(); // Refresh the list
    }




}
