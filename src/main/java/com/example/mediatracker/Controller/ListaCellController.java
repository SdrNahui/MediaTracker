package com.example.mediatracker.Controller;

import com.example.mediatracker.Model.AudioVisual;
import com.example.mediatracker.Model.Serie;
import com.example.mediatracker.Service.MediaImgService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ListaCellController {
    @FXML private HBox root;
    @FXML private Label lblTitulo;
    @FXML private Label lblInfo;
    @FXML private Label lblDescripcion;
    @FXML private Label lblTipo;
    @FXML private ImageView imgPoster;
    private MediaImgService imgService;
    public void setData(AudioVisual av){
        lblTitulo.setText(av.getTitulo());
        lblInfo.setText(av.getGenero() + " • " + av.getPlataforma());
        lblDescripcion.setText(av.getDescripcion());
        lblTipo.setText(av instanceof Serie ? "serie" : "pelicula");
        try {
            imgService = new MediaImgService();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        System.out.println("Img= " + imgService );
        imgPoster.setImage(imgService.cargarImg(av.getImgPath(), 150, 220));
    }
    public void setSeleccionado(boolean seleccionado){
        if(seleccionado){
            root.getStyleClass().add("card-selected");
        } else {
            root.getStyleClass().remove("card-selected");
        }
    }
}
