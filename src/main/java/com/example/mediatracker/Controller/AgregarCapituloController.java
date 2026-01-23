package com.example.mediatracker.Controller;

import com.example.mediatracker.Service.*;
import com.example.mediatracker.Model.Capitulo;
import com.example.mediatracker.Model.Temporada;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AgregarCapituloController implements ISetService, ISetMain {
    @FXML private TextField txtTitulo;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtDuracion;
    @FXML private Label lblMensaje;
    @FXML private CheckBox chkVisto;
    private IService service;
    private MainController main;
    private boolean esEdicion;
    public void setService(IService service){
        this.service= service;
        esEdicion = service.getModoFormulario() == ModoFormulario.EDITAR;
        if(esEdicion){
            cargar(service.getSeleccionadoCap());
        } else {
            service.seleccionadoCap(null);
        }
    }
    public void setMain(MainController main){
        this.main = main;
    }
    @FXML private void guardar(){
        Temporada temporada;
        ResultadoValidacion res;
        if (service.getSeleccionadoTemp() == null){
            return;
        }
        temporada = service.getSeleccionadoTemp();
        String titulo = txtTitulo.getText();
        String descripcion = txtDescripcion.getText();
        boolean visto = chkVisto.isSelected();
        double duracion;
        try {
            duracion = Double.parseDouble(txtDuracion.getText());
            if(esEdicion) {
            res = service.editarCap(service.getSeleccionadoCap(), titulo, descripcion, duracion, visto);
            } else {
                res = service.agregarCapitulo(temporada, titulo, descripcion, duracion, visto);
            }
            if(!res.isOk()){
                tintarMsj(res.getMsj(), TipoMsj.ERROR);
                return;
            }
            tintarMsj(res.getMsj(),TipoMsj.OK);
            if(esEdicion){
                volver();
            } else {
                limpiarForm();
            }
        } catch (NumberFormatException e){
            tintarMsj("Ingrese digitos en la duracion.", TipoMsj.ERROR);
        }
    }
    @FXML private void volver(){
        main.irDetalles();
    }
    private void limpiarForm(){
        txtTitulo.clear();
        txtDescripcion.clear();
        txtDuracion.clear();
        chkVisto.setSelected(false);
    }
    private void cargar(Capitulo c1){
        txtTitulo.setText(c1.getTituloCap());
        txtDescripcion.setText(c1.getDescripcionCap());
        txtDuracion.setText(String.valueOf(c1.getDuracionCap()));
        chkVisto.setSelected(c1.isVisto());
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
}
