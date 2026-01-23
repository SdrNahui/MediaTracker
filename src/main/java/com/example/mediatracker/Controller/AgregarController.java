package com.example.mediatracker.Controller;

import com.example.mediatracker.Model.Pelicula;
import com.example.mediatracker.Service.*;
import com.example.mediatracker.Model.AudioVisual;
import com.example.mediatracker.Model.Serie;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class  AgregarController implements ISetService, ISetMain {
    @FXML private TextField txtTitulo;
    @FXML private TextField txtGenero;
    @FXML private TextArea txtDescripcion;
    @FXML private DatePicker txtAnioPub;
    @FXML private TextField txtPlataforma;
    @FXML private TextField txtDuracion;
    @FXML private CheckBox chkVisto;
    @FXML private TextField txtPuntuacion;
    @FXML private TextArea txtRewiew;
    @FXML private Label lblMensaje;
    @FXML private ComboBox<String> cmbTipo;
    @FXML private ComboBox<String> cmbTipoPel;
    @FXML private TextField txtUrl;
    private IService service;
    private MainController main;
    private MediaImgService imgService;
    private File imgSeleccionada;
    @Override
    public void setService(IService service) {
        this.service = service;
        try {
            this.imgService = new MediaImgService();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
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
        chkVisto.selectedProperty().addListener((obs, old, loVi) -> {
            txtPuntuacion.setDisable(!loVi);
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
        double puntuacion = 0.0;
        String rewiew = txtRewiew.getText();
        String tipo = cmbTipo.getValue();
        String imgPath = null;
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
        if(imgSeleccionada != null){
            try {
                String viejo = service.getModoFormulario() == ModoFormulario.EDITAR
                        ? service.getSeleccionado().getImgPath() : null;
                imgPath = imgService.guardarImg(imgSeleccionada,viejo);
            } catch (IOException e) {
                tintarMsj("Error al guardar la imagen.", TipoMsj.ERROR);
                return;
            }
        } else if (txtUrl.getText() != null && !txtUrl.getText().isBlank()){
            imgPath = txtUrl.getText().trim();
        }
        try{
            anioPub = txtAnioPub.getValue();
            if(tipo.trim().toLowerCase().equals("pelicula")){
                duracion = Double.parseDouble(txtDuracion.getText());
            }
            if(loVi) {
                puntuacion = Double.parseDouble(txtPuntuacion.getText());
            }
            if(service.getModoFormulario() == ModoFormulario.EDITAR) {
                res = service.editar(service.getSeleccionado(),titulo, genero, descripcion, anioPub, plataforma,
                        duracion, loVi, puntuacion, rewiew, tipo, tipoPel, imgPath);
            } else {
                res = service.agregar(titulo, genero, descripcion, anioPub, plataforma,duracion, loVi, puntuacion,
                        rewiew, tipo, tipoPel, imgPath);
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
        txtUrl.setText(a1.getImgPath());
        String tipo = (a1 instanceof Serie) ? "serie" : "pelicula";
        cmbTipo.setValue(tipo);
        actualizarCamposPorTipo(tipo);
        if(a1 instanceof Pelicula pelicula){
            cmbTipoPel.setValue(String.valueOf(pelicula.getTipoPel()));
        }
    }
    @FXML private void seleccionarImg(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Seleccione una imagen");
        chooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg", "*.jpeg"));
        File file = chooser.showOpenDialog(txtTitulo.getScene().getWindow());
        if(file != null){
            imgSeleccionada = file;
        }
    }
}
