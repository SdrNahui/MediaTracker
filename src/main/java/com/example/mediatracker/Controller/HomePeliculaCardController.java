package com.example.mediatracker.Controller;

import com.example.mediatracker.Model.Pelicula;
import com.example.mediatracker.Service.IService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomePeliculaCardController {
    @FXML private Label lblTitulo;
    @FXML private Label lblEstado;
    private IService service;
    private MainController main;
    private Pelicula pelicula;

    public void setData(Pelicula pelicula, IService service, MainController main){
        this.pelicula = pelicula;
        this.service = service;
        this.main = main;
        lblTitulo.setText(pelicula.getTitulo());
        lblEstado.setText("✔ (vista)");
    }
    @FXML private void abrirDetalles(){
        service.seleccionado(pelicula);
        main.irDetalles();
    }
}
