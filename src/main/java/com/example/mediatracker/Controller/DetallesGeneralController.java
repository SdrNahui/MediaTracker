package com.example.mediatracker.Controller;

import com.example.mediatracker.Service.IService;
import com.example.mediatracker.Service.ISetMain;
import com.example.mediatracker.Service.ISetService;
import com.example.mediatracker.Model.AudioVisual;
import com.example.mediatracker.Model.Pelicula;
import com.example.mediatracker.Model.Serie;
import com.example.mediatracker.Model.Temporada;
import com.example.mediatracker.Service.MediaImgService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class DetallesGeneralController implements ISetService, ISetMain {

    @FXML private Label lblTitulo;
    @FXML private Label lblContenido;
    @FXML private Label lblLoVi;
    @FXML private Label lblPuntuacion;
    @FXML private Label lblDuracion;
    @FXML private Label lblDescripcion;
    @FXML private Label lblRewiew;

    @FXML private VBox boxSerie;
    @FXML private Accordion accordionTemp;
    @FXML private ImageView iconVolver;
    @FXML private ImageView imgPoster;
    private IService service;
    private MainController main;
    private MediaImgService imgService;

    @Override
    public void setService(IService service) {
        this.service = service;
        AudioVisual a = service.getSeleccionado();
        if (a == null) return;
        try {
            imgService = new MediaImgService();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        cargarDatosBase(a);
        if (a instanceof Serie serie) {
            mostrarSerie(serie);
        } else if (a instanceof Pelicula pelicula) {
            mostrarPelicula(pelicula);
        }
    }
    @Override
    public void setMain(MainController main) {
        this.main = main;
    }
    @FXML private void initialize(){
        iconVolver.setOnMouseClicked(e -> main.irLista());
    }
    private void cargarDatosBase(AudioVisual a) {
        lblTitulo.setText(a.getTitulo());
        lblContenido.setText(a.getGenero() + " • " + a.getAnioPub() + " • " + a.getPlataforma());
        lblDescripcion.setText(a.getDescripcion());
        lblRewiew.setText(a.getRewiew());
        lblPuntuacion.setText("★ " + a.getPuntuacion());
        lblLoVi.setText(a.isLoVi() ? "Visto" : "No visto");
        imgPoster.setImage(imgService.cargarImg(a.getImgPath(), 150, 220));
    }
    private void mostrarSerie(Serie serie) {
        boxSerie.setVisible(true);
        boxSerie.setManaged(true);
        lblDuracion.setText(serie.getDuracion() + " min (total)");
        cargarTemporadas(serie);
    }
    private void mostrarPelicula(Pelicula pelicula) {
        boxSerie.setVisible(false);
        boxSerie.setManaged(false);
        lblDuracion.setText(pelicula.getDuracion() + " min" + " • " + pelicula.getTipoPel());
    }
    private void cargarTemporadas(Serie serie) {
        accordionTemp.getPanes().clear();
        for (Temporada t : serie.getTemporadas()) {
            accordionTemp.getPanes().add(crearTemporada(t));
        }
    }
    @FXML
    private void agregarTemp() {
        Serie serie = null;
        if(service.getSeleccionado() instanceof Serie){
            serie = (Serie) service.getSeleccionado();
        }
        service.agregarTemporada(serie);
        main.irDetalles();
    }
    private TitledPane crearTemporada(Temporada t){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mediatracker/tempView.fxml"));
            Pane vista = loader.load();
            TempController controller = loader.getController();
            controller.setView(service,main,t);
            TitledPane pane = new TitledPane("Temporada " + t.getNumTemp(), vista);
            pane.setExpanded(false);
            return pane;
        } catch (Exception e){
            e.printStackTrace();
            return  new TitledPane("Error", new Label("No se pudo cargar la temporada"));
        }
    }
}
