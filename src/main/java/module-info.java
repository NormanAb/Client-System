module com.benedict.minibank {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.commons;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires java.desktop;
    requires org.apache.pdfbox;
    requires javafx.swing;


    opens com.benedict.minibank to javafx.fxml;
    opens com.benedict.minibank.Controllers to javafx.fxml;
    exports com.benedict.minibank;
    exports com.benedict.minibank.Controllers;
    exports com.benedict.minibank.Models;
    exports com.benedict.minibank.Views;
    exports com.benedict.minibank.Services.dao;
}