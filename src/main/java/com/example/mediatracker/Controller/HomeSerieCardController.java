package com.example.mediatracker.Controller;

import com.example.mediatracker.Model.Capitulo;
import com.example.mediatracker.Model.Serie;
import com.example.mediatracker.Model.Temporada;
import com.example.mediatracker.Service.IService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;

public class HomeSerieCardController {

    @FXML private Label lblTitulo;
    @FXML private Label lblEstado;
    @FXML private ProgressBar progreso;

    private IService service;
    private MainController main;
    private Serie serie;

    public void setData(Serie serie, IService service, MainController main) {
        this.serie = serie;
        this.service = service;
        this.main = main;
        lblTitulo.setText(serie.getTitulo());
        actualizarEstado();
    }

    /* -------------------------
       ACCIONES DE UI
       ------------------------- */

    @FXML private void abrirDetalles() {
        service.seleccionado(serie);
        main.irDetalles();
    }

    @FXML private void marcarSiguienteComoVisto(MouseEvent e) {
        e.consume();
        Capitulo siguiente = buscarSiguienteCapituloNoVisto();
        if (siguiente == null) return;

        Temporada temp = buscarTemporadaDeCapitulo(siguiente);
        service.marcarVisto(serie, temp, siguiente);

        actualizarEstado();
    }

    /* -------------------------
       MÉTODOS DE APOYO
       ------------------------- */

    private void actualizarEstado() {
        int vistos = 0;
        int total = 0;

        for (Temporada t : serie.getTemporadas()) {
            for (Capitulo c : t.getCapitulos()) {
                total++;
                if (c.isVisto()) vistos++;
            }
        }

        lblEstado.setText(vistos + " / " + total + " episodios vistos");
        progreso.setProgress(total == 0 ? 0 : (double) vistos / total);
    }

    private Capitulo buscarSiguienteCapituloNoVisto() {
        for (Temporada t : serie.getTemporadas()) {
            for (Capitulo c : t.getCapitulos()) {
                if (!c.isVisto()) {
                    return c;
                }
            }
        }
        return null; // ya está todo visto
    }

    private Temporada buscarTemporadaDeCapitulo(Capitulo cap) {
        for (Temporada t : serie.getTemporadas()) {
            if (t.getCapitulos().contains(cap)) {
                return t;
            }
        }
        return null;
    }
}
