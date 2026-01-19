package com.example.mediatracker.Controller;

import com.example.mediatracker.Service.IService;
import com.example.mediatracker.Service.ISetMain;
import com.example.mediatracker.Service.ISetService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class MenuController implements ISetService, ISetMain {
    @FXML private javafx.scene.control.Label labelHome;
    @FXML private javafx.scene.control.Label labelAgregar;
    @FXML private javafx.scene.control.Label labelBiblo;
    @FXML private Label labelTotal;
    @FXML private ImageView iconHome;
    @FXML private ImageView iconAgregar;
    @FXML private ImageView iconBiblo;
    @FXML private ImageView iconTotal;

    private IService service;
    private MainController main;

    public void setService(IService service){
        this.service = service;
    }
    public void setMain (MainController main){
        this.main = main;
    }
    @FXML public void initialize() {
        setTextos(false);
        iconHome.setOnMouseClicked(e -> main.irHome());
        iconAgregar.setOnMouseClicked(e -> main.irAgregar());
        iconBiblo.setOnMouseClicked(e -> main.irLista());
    }
    public void setTextos(boolean visible){
        labelHome.setVisible(visible);
        labelAgregar.setVisible(visible);
        labelBiblo.setVisible(visible);
        labelTotal.setVisible(visible);
    }
}
