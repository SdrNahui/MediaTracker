package com.example.mediatracker.Controller;

import com.example.mediatracker.Service.IService;
import com.example.mediatracker.Model.Capitulo;
import com.example.mediatracker.Model.Serie;
import com.example.mediatracker.Model.Temporada;
import com.example.mediatracker.Service.TipoMsj;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;

public class TempController {
    @FXML private Label lblTemp;
    @FXML private ListView<Capitulo> listaCaps;
    @FXML private Label lblMensaje;
    private ObservableList<Capitulo> capitulosObs;
    private IService service;
    private MainController main;
    private Temporada temporada;

    public void setView(IService service, MainController main,Temporada temporada) {
        this.service = service;
        this.temporada = temporada;
        this.main = main;
        capitulosObs = FXCollections.observableArrayList(temporada.getCapitulos());
        listaCaps.setItems(capitulosObs);
        lblTemp.textProperty().bind(Bindings.createStringBinding(
                () -> {
            int cantidad = capitulosObs.size();
            String textoCap = cantidad ==  1 ? " episodio" : " episodios";
            return "Temporada " + temporada.getNumTemp() + "\n" + cantidad + textoCap;
        }, capitulosObs));
        listaCaps.setCellFactory(list -> new ListCell<>(){
            private FXMLLoader loader;
            private CapCellController controller;
            @Override
            protected void updateItem(Capitulo cap, boolean empty){
                super.updateItem(cap,empty);
                if(empty || cap == null){
                    setText(null);
                    setGraphic(null);
                    return;
                }
                try {
                    if(loader == null){
                        loader = new FXMLLoader(getClass().getResource("/com/example/mediatracker/capCellView.fxml"));
                        setGraphic(loader.load());
                        controller = loader.getController();
                    }
                    controller.setData(cap);
                    setPrefWidth(listaCaps.getWidth());
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

    }
    @FXML private void agregarCap(){
        service.seleccionadoTemp(temporada);
        main.irAgregarCap();
    }
    @FXML private void editarCap(){
        Capitulo seleccionado = listaCaps.getSelectionModel().getSelectedItem();
        if(seleccionado == null){
            tintarMsj("Primero selecciona el capitulo para editar.",TipoMsj.ERROR);
            return;
        }
        service.seleccionadoTemp(temporada);
        service.seleccionadoCap(seleccionado);
        main.irEditarCap();
    }
    @FXML private void eliminarCap(){
        Capitulo seleccionado = listaCaps.getSelectionModel().getSelectedItem();
        if(seleccionado == null){
            tintarMsj("Primero selecciona el capitulo para eliminar.",TipoMsj.ERROR);
            return;
        }
        if (!alertaCap(seleccionado)){
            return;
        }
        service.eliminarCap(temporada,seleccionado);
        tintarMsj(service.getMensaje(),TipoMsj.OK);
        listaCaps.refresh();
        capitulosObs.setAll(temporada.getCapitulos());
    }
    @FXML private void eliminarTemp(){
        if(!alertaTemp(temporada)){
            return;
        }
        Serie serie = (Serie) service.getSeleccionado();
        service.eliminarTemp(serie,temporada);
        main.irDetalles();
    }
    private boolean alertaTemp(Temporada temporada){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar temporada");
        alert.setHeaderText("¿Eliminar temporada " + temporada.getNumTemp() + "?");
        alert.setContentText("Se eliminaran todos los capitulos.");
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }
    private boolean alertaCap(Capitulo capitulo){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar Capitulo");
        alert.setHeaderText("¿Eliminar capitulo " + capitulo.getNumCap() + "?");
        alert.setContentText("Se eliminará el capitulo.");
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }
    private void tintarMsj(String msj, TipoMsj tipoMsj) {
        lblMensaje.setText(msj);
        switch (tipoMsj) {
            case OK -> lblMensaje.setStyle("-fx-text-fill: #1E90FF;");
            case INFO -> lblMensaje.setStyle("");
            case ERROR -> lblMensaje.setStyle("-fx-text-fill: red;");
            case null -> lblMensaje.setStyle(null);
        }
    }

}
