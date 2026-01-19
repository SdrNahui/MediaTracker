package com.example.mediatracker.Service;

import com.example.mediatracker.Model.*;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public interface IService {
    //AUDIOVISUAL
    public ResultadoValidacion agregar(String titulo, String genero, String descripcion, LocalDate anioPub,
                                       String plataforma, double duracion, boolean loVi, double puntuacion,
                                       String rewiew, String tipo, String tipoPel);
    public ResultadoValidacion editar(AudioVisual audioVisual, String titulo, String genero, String descripcion, LocalDate anioPub,
                                      String plataforma, double duracion, boolean loVi, double puntuacion,
                                      String rewiew, String tipo, String tipoPel);
    public void eliminar(AudioVisual audioVisualSelec);
    public void seleccionado(AudioVisual audioVisual);
    public boolean estaSeleccionado();
    public AudioVisual getSeleccionado();

    //TEMPORADA
    public void agregarTemporada(Serie s);
    public void editarTemp();
    public void eliminarTemp(Serie s, Temporada temporada);
    public void seleccionadoTemp(Temporada temporada);
    public boolean estaSeleccionadoTemp();
    public Temporada getSeleccionadoTemp();

    //CAPITULO
    public ResultadoValidacion agregarCapitulo(Temporada t, String titulo, String descripcion, double duracion, boolean visto);
    public ResultadoValidacion editarCap(Capitulo capitulo, String titulo, String descripcion, double duracion, boolean visto);
    public void eliminarCap(Temporada temporada, Capitulo capitulo);
    public void seleccionadoCap(Capitulo capitulo);
    public boolean estaSeleccionadoCap();
    public Capitulo getSeleccionadoCap();

    //BUSCAR
    public boolean buscarInteligente(AudioVisual audioVisual, String texto);

    //RERSPUESTA
    public String getMensaje();
    public void setMensaje(String mensaje);

    //FORMULARIO PARA SETEAR
    public ModoFormulario getModoFormulario();
    public void setModoFormulario(ModoFormulario modoFormulario);
    public ObservableList getAudioVisuales();
    public List<Serie> getSeries();
    public List<Pelicula> getPeliculasRecientes();
    public ObservableList<Actividad> getActividades();

    //REGISTRAR ACVITIDADES
    public void registrarActividades(Actividad a);
    public void registrarAudioVisual(AudioVisual av);
    public void registrarEdicionAV(AudioVisual av);
    public void registrarEliminacionAV(AudioVisual av);
    public void registrarTemporada(Serie s, Temporada t);
    public void registrarEminacionTemp(Serie s, Temporada t);
    public void registrarCapitulo(Serie s, Temporada t, Capitulo c);
    public void registrarEdicionCap(Serie s, Temporada t, Capitulo c);
    public void registrarEliminacionCap(Serie s, Temporada t, Capitulo c);
    public void marcarVisto(Serie serie, Temporada temporada, Capitulo capitulo);
    public ObservableIntegerValue version();
    public void marcarVisto(Pelicula pelicula);
    public void limpiarForm();
}
