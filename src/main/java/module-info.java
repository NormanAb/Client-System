module com.benedict.minibank {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.commons;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires java.desktop;
    requires org.apache.pdfbox;
    requires javafx.swing;


    opens com.ClientManagement to javafx.fxml;
    opens com.ClientManagement.Controllers to javafx.fxml;
    exports com.ClientManagement;
    exports com.ClientManagement.Controllers;
    exports com.ClientManagement.Models;
    exports com.ClientManagement.Views;
    exports com.ClientManagement.Services.dao;
}