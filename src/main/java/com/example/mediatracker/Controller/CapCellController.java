package com.example.mediatracker.Controller;

import com.example.mediatracker.Model.Capitulo;
import com.example.mediatracker.Service.IService;
import com.example.mediatracker.Service.ISetMain;
import com.example.mediatracker.Service.ISetService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CapCellController implements ISetService, ISetMain {
    @FXML private HBox root;
    @FXML private Label lblVisto;
    @FXML private Label lblDescripcion;
    @FXML private Label lblTitulo;
    @FXML private Label lblInfo;

    private IService service;
    private MainController main;

    @Override
    public void setService(IService service) {
        this.service = service;
    }
    @Override
    public void setMain(MainController main){
        this.main = main;
    }
    public void setData(Capitulo cap){
        lblTitulo.setText("E" + cap.getNumCap() + " - " + cap.getTituloCap());
        lblDescripcion.setText(cap.getDescripcionCap());
        lblInfo.setText(cap.getDuracionCap() + " min");
        if(cap.isVisto()){
            lblVisto.setText("✔");
            lblVisto.getStyleClass().setAll("cap-visto");
        } else {
            lblVisto.setText("❌");
            lblVisto.getStyleClass().setAll("cap-no-visto");
        }
    }
}
