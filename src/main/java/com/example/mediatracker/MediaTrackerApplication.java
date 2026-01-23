package com.example.mediatracker;

import com.example.mediatracker.Controller.MainController;
import com.example.mediatracker.Service.IService;
import com.example.mediatracker.Service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MediaTrackerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        IService service = new Service();
        FXMLLoader fxmlLoader = new FXMLLoader(MediaTrackerApplication.class.getResource("mainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainController controller = fxmlLoader.getController();
        controller.setService(service);
        stage.setTitle("MediaTracker v1.0");
        scene.getStylesheets().add(getClass().getResource("nightwing.css").toExternalForm());
        stage.setHeight(780);
        stage.setWidth(1200);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("logo.png")));
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
