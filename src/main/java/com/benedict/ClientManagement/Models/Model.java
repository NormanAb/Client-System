package com.benedict.ClientManagement.Models;

import com.benedict.ClientManagement.Services.dao.ClientDAO;
import com.benedict.ClientManagement.Services.dao.ReportDAO;
import com.benedict.ClientManagement.Services.dao.UserDAO;
import com.benedict.ClientManagement.Views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.image.BufferedImage;

import java.time.LocalDate;
import java.util.logging.Logger;

public class Model {

    private static Model model;
    private static final Logger logger = Logger.getLogger(Model.class.getName());

    private final ViewFactory viewFactory;
    public final UserDAO userDAO;
    public final ClientDAO authorDAO;
    public final ReportDAO reportDAO;
    public final ClientDAO clientDAO;
    private boolean loginSuccessFlag;

    private final ObservableList<Client> clients;
    private final ObservableList<Report> reports;
    private User currentUser;

    private Model() {
        this.viewFactory = new ViewFactory();
        this.userDAO = new UserDAO(new DatabaseDriver().getConnection());
        this.authorDAO = new ClientDAO(new DatabaseDriver().getConnection());
        this.clientDAO = new ClientDAO(new DatabaseDriver().getConnection());
        this.reportDAO = new ReportDAO(new DatabaseDriver().getConnection());
        this.loginSuccessFlag = false;
        this.currentUser = null;
        this.clients = FXCollections.observableArrayList();
        this.reports = FXCollections.observableArrayList();
        logger.info("Model initialized with DAOs and empty data lists");
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
            logger.info("Model instance created");
        }
        return model;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public boolean getAdminSuccessFlag() {
        return this.loginSuccessFlag;
    }

    public void setClientAdminSuccessFlag(boolean flag) {
        this.loginSuccessFlag = flag;
        logger.info("Login success flag set to: " + flag);
    }

    public boolean hasRegisteredUsers() {
        boolean hasUsers = userDAO.countUsers() > 0;
        logger.info("Checking registered users; result: " + hasUsers);
        return hasUsers;
    }

    public boolean isUserAlreadyRegistered(String userName) {
        boolean exists = userDAO.isUserExist(userName);
        logger.info("User " + userName + " already registered: " + exists);
        return exists;
    }

    public void createUser(String userName, String password) {
        logger.info("Creating user: " + userName);
        userDAO.createUser(userName, password, LocalDate.now());
    }

    public void checkCredentials(String userName, String password) {
        logger.info("Checking credentials for user: " + userName);
        User user = userDAO.findUserByCredentials(userName, password);
        if (user != null) {
            this.loginSuccessFlag = true;
            this.currentUser = user;
            logger.info("User " + userName + " successfully logged in.");
        } else {
            logger.warning("Invalid credentials for user: " + userName);
        }
    }

    public String getLoggedUserName() {
        return currentUser != null ? currentUser.usernameProperty() : null;
    }

    public int getLoggedUserId() {
        if (currentUser != null) {
            return currentUser.getId();
        } else {
            logger.warning("Attempted to get logged user id but no user is logged in.");
            return -1;
        }
    }

    public void deleteClient(int id) {
        logger.info("Deleting client with id: " + id);
        clientDAO.delete(id);
    }


    // Client creator
    public void createClient(String name, String surname, String email, String phone, String status) {
        logger.info("Creating client: " + name + " " + surname + " with status: " + status);
        // This constructor sets the date to LocalDate.now() automatically.
        Client client = new Client(name, surname, email, phone, status);
        clientDAO.create(client);
        loadClients();
    }

    public void updateClient(Client client) {
        logger.info("Updating client with id: " + client.getId());
        clientDAO.update(client);
    }

    // Clients
    public ObservableList<Client> getClients() {
        logger.info("Retrieving all clients from DAO");
        return clientDAO.findAll();
    }

    public void loadClients() {
        logger.info("Loading clients into observable list");
        this.clients.clear();
        this.clients.addAll(clientDAO.findAll());
        logger.info("Total clients loaded: " + clients.size());
    }

    // Reports
    public ObservableList<Report> getReports() {
        logger.info("Retrieving reports from DAO");
        ObservableList<Report> daoReports = reportDAO.findAll();
        logger.info("Reports retrieved: " + daoReports.size());
        return daoReports;
    }

    public void loadReports() {
        logger.info("Loading reports into observable list");
        reports.clear();
        reports.addAll(reportDAO.findAll());
        logger.info("Total reports loaded: " + reports.size());
    }

    public void createReport(String name, LocalDate date) {
        logger.info("Creating report with name: " + name + " and date: " + date);
        Report report = new Report(-1, date, name); // ID will be assigned by the database
        reportDAO.create(report);
        loadReports();
        logger.info("Report created and reports list refreshed.");
    }


    public BufferedImage getBarChartSnapshot() {
        return viewFactory.getDashboardController().captureBarChartSnapshot();
    }

    public BufferedImage getPieChartSnapshot() {
        return viewFactory.getDashboardController().capturePieChartSnapshot();
    }
}
