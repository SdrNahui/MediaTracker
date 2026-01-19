package com.example.mediatracker.Controller;

import com.example.mediatracker.Model.Pelicula;
import com.example.mediatracker.Service.*;
import com.example.mediatracker.Model.AudioVisual;
import com.example.mediatracker.Model.Serie;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AgregarController implements ISetService, ISetMain {

    @FXML TextField txtTitulo;
    @FXML TextField txtGenero;
    @FXML TextArea txtDescripcion;
    @FXML DatePicker txtAnioPub;
    @FXML TextField txtPlataforma;
    @FXML TextField txtDuracion;
    @FXML CheckBox chkVisto;
    @FXML TextField txtPuntuacion;
    @FXML TextArea txtRewiew;
    @FXML Label lblMensaje;
    @FXML ComboBox<String> cmbTipo;
    @FXML ComboBox<String> cmbTipoPel;
    private IService service;
    private MainController main;
    @Override
    public void setService(IService service) {
        this.service = service;
        if (service.getModoFormulario() == ModoFormulario.EDITAR) {
            cargarDatos(service.getSeleccionado());
        }
    }
    public void setMain(MainController main){
        this.main = main;
    }
    @FXML
    private void initialize() {
        cmbTipo.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) return;
            actualizarCamposPorTipo(newVal);
        });
    }
    @FXML private void guardar(){
        ResultadoValidacion res;
        String titulo = txtTitulo.getText();
        String genero = txtGenero.getText();
        String descripcion = txtDescripcion.getText();
        LocalDate anioPub;
        String plataforma = txtPlataforma.getText();
        double duracion = 0;
        boolean loVi = chkVisto.isSelected();
        double puntuacion;
        String rewiew = txtRewiew.getText();
        String tipo = cmbTipo.getValue();
        if(tipo == null || tipo.isBlank()){
            tintarMsj("Seleccione un tipo", TipoMsj.ERROR);
            return;
        }
        String tipoPel = null;
        if(tipo.equalsIgnoreCase("pelicula")){
            tipoPel = cmbTipoPel.getValue();
            if(tipoPel == null || tipoPel.isBlank()){
                tintarMsj("Seleccione un tipo de pelicula", TipoMsj.ERROR);
                return;
            }
        }
        try{
            anioPub = txtAnioPub.getValue();
            if(tipo.trim().toLowerCase().equals("pelicula")){
                duracion = Double.parseDouble(txtDuracion.getText());
            }
            puntuacion = Double.parseDouble(txtPuntuacion.getText());
            if(service.getModoFormulario() == ModoFormulario.EDITAR) {
                res = service.editar(service.getSeleccionado(),titulo, genero, descripcion, anioPub, plataforma,
                        duracion, loVi, puntuacion, rewiew, tipo, tipoPel);
            } else {
                res = service.agregar(titulo, genero, descripcion, anioPub, plataforma,duracion, loVi, puntuacion,
                        rewiew, tipo, tipoPel);
            }
            if(!res.isOk()){
                tintarMsj(res.getMsj(), TipoMsj.ERROR);
                return;
            }
            tintarMsj(res.getMsj(),TipoMsj.OK);
            if(service.getModoFormulario() == ModoFormulario.EDITAR){
                main.irLista();
            }else{
                limparForm();
            }
        } catch (NumberFormatException e) {
            tintarMsj("Los campos numericos no deben estar vacios o tiene que ser digitos.", TipoMsj.ERROR);
        } catch (DateTimeParseException e){
            tintarMsj("Fecha mal ingresada.", TipoMsj.ERROR);
        }
    }
    private void limparForm(){
        txtTitulo.clear();
        txtGenero.clear();
        txtDescripcion.clear();
        txtAnioPub.setValue(null);
        txtAnioPub.getEditor().clear();
        txtPlataforma.clear();
        txtDuracion.clear();
        chkVisto.setSelected(false);
        txtPuntuacion.clear();
        txtRewiew.clear();
        cmbTipo.setValue(null);
        actualizarCamposPorTipo(null);
        cmbTipoPel.setValue(null);
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
    private void actualizarCamposPorTipo(String tipo) {
        boolean esSerie = "Serie".equalsIgnoreCase(tipo);
        txtDuracion.setVisible(!esSerie);
        txtDuracion.setManaged(!esSerie);
        cmbTipoPel.setVisible(!esSerie);
        cmbTipoPel.setManaged(!esSerie);
    }
    private void cargarDatos(AudioVisual a1){
        txtTitulo.setText(a1.getTitulo());
        txtGenero.setText(a1.getGenero());
        txtDescripcion.setText(a1.getDescripcion());
        txtAnioPub.setValue(a1.getAnioPub());
        txtPlataforma.setText(a1.getPlataforma());
        txtDuracion.setText(String.valueOf(a1.getDuracion()));
        chkVisto.setSelected(a1.isLoVi());
        txtPuntuacion.setText(String.valueOf(a1.getPuntuacion()));
        txtRewiew.setText(a1.getRewiew());
        String tipo = (a1 instanceof Serie) ? "serie" : "pelicula";
        cmbTipo.setValue(tipo);
        actualizarCamposPorTipo(tipo);
        if(a1 instanceof Pelicula pelicula){
            cmbTipoPel.setValue(String.valueOf(pelicula.getTipoPel()));
        }
    }
}
