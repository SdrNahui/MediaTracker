package com.example.mediatracker.Controller;

import com.example.mediatracker.Model.*;
import com.example.mediatracker.Service.IService;
import com.example.mediatracker.Service.ISetMain;
import com.example.mediatracker.Service.ISetService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import javax.swing.text.LabelView;
import java.io.IOException;

public class HomeController implements ISetService, ISetMain {
    @FXML private FlowPane flowEnCurso;
    @FXML private VBox boxActividad;
    @FXML private VBox boxPeliculas;
    @FXML private Label lblTotalSeries;
    @FXML private Label lblCapsVistos;
    private IService service;
    private MainController main;

    @Override
    public void setService(IService service) {
        this.service = service;
        cargarDatos();
        service.version().addListener((observableValue, oldN, newN) -> {
            refrescarHome();
        } );
    }
    @Override
    public void setMain(MainController main) {
        this.main = main;
    }
    private void cargarDatos(){
        cargarSeriesEnCurso();
        cargarActividad();
        cargarPeliculasVistas();
        cargarStats();
    }
    private void cargarSeriesEnCurso(){
        flowEnCurso.getChildren().clear();
        boolean estaEnCurso = false;
        for(Serie s : service.getSeries()){
            if(estaEncurso(s)){
                flowEnCurso.getChildren().add(crearCardSerie(s));
                estaEnCurso = true;
            }
        }
        if(!estaEnCurso){
            flowEnCurso.getChildren().add(msjVacio("No hay series en curso."));
        }
    }
    private void cargarPeliculasVistas() {
        boxPeliculas.getChildren().clear();
        if(service.getPeliculasRecientes().isEmpty()){
            boxPeliculas.getChildren().add(msjVacio("No hay peliculas vistas."));
        }
        for (Pelicula p : service.getPeliculasRecientes()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass()
                        .getResource("/com/example/mediatracker/homePeliculaCard.fxml"));
                Parent card = loader.load();
                HomePeliculaCardController c = loader.getController();
                c.setData(p, service, main);
                boxPeliculas.getChildren().add(card);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private boolean estaEncurso(Serie serie){
        int vistos = 0;
        int total = 0;
        for(Temporada t : serie.getTemporadas()){
            for(Capitulo c : t.getCapitulos()){
                total++;
                if(c.isVisto()){
                    vistos++;
                }
            }
        }
        return total > 0 && vistos < total && vistos > 0;
    }
    private Parent crearCardSerie(Serie serie){
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/com/example/mediatracker/homeSerieCardView.fxml"));
            Parent card = loader.load();
            HomeSerieCardController controller = loader.getController();
            controller.setData(serie, service, main);
            return card;
        } catch (IOException e){
            e.printStackTrace();
            return new Label("Error cargando la serie");
        }
    }
    private void cargarActividad(){
        boxActividad.getChildren().clear();
        ObservableList<Actividad> actividades = service.getActividades();
        if(actividades.isEmpty()){
            boxActividad.getChildren().add(msjVacio("todavia no hay actividad."));
            return;
        }
        int max = Math.min(10, actividades.size());
        for (int i = 0; i < max; i++){
            Actividad a = actividades.get(i);
            Label lbl = new Label(a.getMsj());
            lbl.setStyle("-fx-text-fill: #cbd5e1");
            boxActividad.getChildren().add(lbl);
        }
    }
    private void cargarStats(){
        int totalSeries = service.getSeries().size();
        int capsVistos = 0;
        for(Serie s1 : service.getSeries()){
            for(Temporada t1 : s1.getTemporadas()){
                for(Capitulo c1 : t1.getCapitulos()){
                    if(c1.isVisto()) {
                        capsVistos ++;
                    }
                }
            }
        }
        lblTotalSeries.setText(String.valueOf(totalSeries));
        lblCapsVistos.setText(String.valueOf(capsVistos));
    }
    private void refrescarHome(){
        cargarSeriesEnCurso();
        cargarPeliculasVistas();
        cargarActividad();
        cargarStats();
    }
    private Label msjVacio(String texto){
        Label vacio = new Label(texto);
        vacio.setStyle("-fx-text-fill: #64748b;");
        return vacio;
    }
}
