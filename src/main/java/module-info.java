module com.benedict.minibank {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.commons;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires java.desktop;
    requires org.apache.pdfbox;
    requires javafx.swing;


    opens com.benedict.ClientManagement to javafx.fxml;
    opens com.benedict.ClientManagement.Controllers to javafx.fxml;
    exports com.benedict.ClientManagement;
    exports com.benedict.ClientManagement.Controllers;
    exports com.benedict.ClientManagement.Models;
    exports com.benedict.ClientManagement.Views;
    exports com.benedict.ClientManagement.Services.dao;
}