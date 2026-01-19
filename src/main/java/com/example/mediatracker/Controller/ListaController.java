package com.example.mediatracker.Controller;

import com.example.mediatracker.Service.IService;
import com.example.mediatracker.Service.ISetMain;
import com.example.mediatracker.Service.ISetService;
import com.example.mediatracker.Model.AudioVisual;
import com.example.mediatracker.Service.TipoMsj;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class ListaController implements ISetService, ISetMain {
    @FXML TextField txtBuscar;
    @FXML Label lblVacio;
    @FXML Label lblMensaje;
    @FXML FlowPane contenedor;
    private ListaCellController cardSelec;
    private IService service;
    private MainController main;
    @Override
    public void setService(IService service) {
        this.service = service;
        service.getAudioVisuales().addListener(
                (ListChangeListener<AudioVisual>) change -> {
                    cargar(service.getAudioVisuales());} );
        cargar(service.getAudioVisuales());
    }
    public void setMain(MainController main){
        this.main = main;
    }
    public void initialize(){
        txtBuscar.textProperty().addListener((observableValue, s, fil) -> buscar());
    }
    private void cargar(List<AudioVisual> lista){
        contenedor.getChildren().clear();
        if(lista.isEmpty()){
            lblVacio.setVisible(true);
            return;
        }
        lblVacio.setVisible(false);
        for (AudioVisual av : lista){
            contenedor.getChildren().add(crearCard(av));
        }
    }
    private Parent crearCard(AudioVisual av){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/com/example/mediatracker/listaCellView.fxml"));
            Parent card = loader.load();
            ListaCellController controller = loader.getController();
            controller.setData(av);
            card.setOnMouseClicked(e -> {
                if(cardSelec !=null){
                    cardSelec.setSeleccionado(false);
                }
                controller.setSeleccionado(true);
                cardSelec = controller;
                service.seleccionado(av);
                if(e.getClickCount() == 2){
                    animarAnimaciones(card, () -> main.irDetalles());
                    e.consume();
                }
            });
            return card;
        } catch (IOException e){
            e.printStackTrace();
            return  new VBox();
        }
    }
    @FXML private void buscar(){
        String texto = txtBuscar.getText();
        List<AudioVisual> resultado = service.getAudioVisuales().stream()
                .filter(av ->service.buscarInteligente((AudioVisual) av,texto)).toList();
        cargar(resultado);
    }
    @FXML private void editar(){
        AudioVisual seleccionado = /*vista.getSelectionModel().getSelectedItem(); */ service.getSeleccionado();
        if(seleccionado == null){
            /*lblMensaje.setStyle("-fx-text-fill: #64587f;");
            lblMensaje.setText("Primero selecciona lo que quieras editar");*/
            tintarMsj("Primero seleccione que quiere editar.", TipoMsj.ERROR);
            return;
        }
        service.seleccionado(seleccionado);
        main.irEditar();
    }
    @FXML private void eliminar(){
        AudioVisual seleccionado =service.getSeleccionado();
        if(seleccionado == null){

            tintarMsj("Primero seleccione que quiere eliminar.", TipoMsj.ERROR);
            return;
        }
        Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("Eliminar serie/pelicula?");
        alert.setContentText("Seguro que quiere eliminar " + seleccionado.getTitulo() + "?");
        alert.showAndWait().ifPresent(respuesta -> {
            if(respuesta == ButtonType.OK){
                service.eliminar(seleccionado);
                lblMensaje.setText(service.getMensaje());
                //cargar(service.getAudioVisuales());
            }
        });
    }
    @FXML private void detalles() {
        AudioVisual seleccionado = service.getSeleccionado();
        if (seleccionado == null) {
            tintarMsj("Primero seleccione que quiere detallar.", TipoMsj.ERROR);
            return;
        }
        main.irDetalles();
    }
    private void tintarMsj(String msj, TipoMsj tipoMsj){
        lblMensaje.setText(msj);
        switch (tipoMsj){
            case OK -> lblMensaje.setStyle("-fx-text-fill: #1E90FF;");
            case INFO ->  lblMensaje.setStyle("");
            case ERROR -> lblMensaje.setStyle("-fx-text-fill: red;");
            case null -> lblMensaje.setStyle(null);
        }
    }

    private void animarAnimaciones(Node card, Runnable onFinish){
        ScaleTransition scale = new ScaleTransition(Duration.millis(150), card);
        scale.setFromX(1);
        scale.setFromY(1);
        scale.setToX(1.05);
        scale.setToY(1.05);
        scale.setAutoReverse(true);
        FadeTransition fade = new FadeTransition(Duration.millis(150), card);
        fade.setFromValue(1);
        fade.setToValue(0.85);
        fade.setAutoReverse(false);
        fade.setCycleCount(2);
        ParallelTransition anim = new ParallelTransition(scale, fade);
        anim.setOnFinished(e -> onFinish.run());
        anim.play();
    }

}