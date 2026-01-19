module com.example.mediatracker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.base;
    requires com.google.gson;
    opens com.example.mediatracker to javafx.fxml, com.google.gson;
    exports com.example.mediatracker;
    exports com.example.mediatracker.Model;
    opens com.example.mediatracker.Model to com.google.gson, javafx.fxml;
    exports com.example.mediatracker.Controller;
    opens com.example.mediatracker.Controller to com.google.gson, javafx.fxml;
    exports com.example.mediatracker.Service;
    opens com.example.mediatracker.Service to com.google.gson, javafx.fxml;
}