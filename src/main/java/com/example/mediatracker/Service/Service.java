package com.example.mediatracker.Service;

import com.example.mediatracker.Repository.ActividadRepository;
import com.example.mediatracker.Repository.IRepository;
import com.example.mediatracker.Repository.JsonRepository;
import com.example.mediatracker.Model.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Service implements IService {
    private final IRepository<AudioVisual> repository;
    private final ObservableList<AudioVisual> audioVisuales;
    private final ObservableList<Actividad> actividades;
    private final ActividadRepository actRepository;
    private final IntegerProperty version = new SimpleIntegerProperty(0);
    private ModoFormulario modoFormulario;
    private AudioVisual audioVisualSelect;
    private Temporada temporadaSelect;
    private Capitulo capituloSelect;
    private String mensaje;
    //Persistencia
    public Service(){
        repository = new JsonRepository();
        audioVisuales = FXCollections.observableArrayList(repository.load());
        actRepository = new ActividadRepository();
        actividades =  FXCollections.observableArrayList(actRepository.load());
    }
    private void guardarCambios(){
        repository.save(new ArrayList<>(audioVisuales));
    }

    //AUDIOVISUAL
    @Override
    public ResultadoValidacion agregar(String titulo, String genero,String descripcion, LocalDate anioPub,
                                       String plataforma, double duracion, boolean loVi, double puntuacion,
                                       String rewiew, String tipo, String tipoPel, String imgPath) {
        if(titulo == null || tipo.isBlank()){
            setMensaje("El titulo no puede estar vacio.");
            return new ResultadoValidacion(false, getMensaje());
        }
        if(genero == null || genero.isBlank()){
            setMensaje("El genero no puede estar vacio.");
            return new ResultadoValidacion(false, getMensaje());
        }
        if(plataforma == null || plataforma.isBlank()){
            setMensaje("La plataforma no puede estar vacia.");
            return new ResultadoValidacion(false, getMensaje());
        }
        if (tipo.trim().toLowerCase().equalsIgnoreCase("pelicula")) {
            if (duracion < 5) {
                setMensaje("La duración no puede ser menor a 5.");
                return new ResultadoValidacion(false, getMensaje());
            }
        }
        if(!loVi && puntuacion > 0){
            setMensaje("La puntuación no puede ser puntuada si no se vio.");
            return new ResultadoValidacion(false, getMensaje());
        }
        if(puntuacion < 0.0 || puntuacion > 10.0){
            setMensaje("La puntuación no puede ser menor o igual a 0.0 ni mayor a 10.0");
            return new ResultadoValidacion(false, getMensaje());
        }
        if(tipo != null){
            tipo = tipo.trim().toLowerCase();
        }
        if(tipoPel != null){
            tipoPel = tipoPel.trim().toLowerCase();
        }
        ResultadoValidacion res = null;
            if (tipo.equals("serie")){
                ResultadoValidacion rS1 = agregarSerie(titulo,genero,descripcion,anioPub,plataforma,duracion,loVi
                        ,puntuacion,rewiew, imgPath);
                res = rS1;
            } else if (tipo.equals("pelicula")){
                ResultadoValidacion rP1 = agregarPelicula(titulo, genero,descripcion, anioPub, plataforma, duracion,
                        loVi, puntuacion, rewiew, tipoPel, imgPath);
                res = rP1;
            }

        return res;
    }
    private ResultadoValidacion agregarSerie(String titulo, String genero,String descripcion, LocalDate anioPub,
                                             String plataforma, double duracion, boolean loVi, double puntuacion,
                                             String rewiew, String imgPath){
        AudioVisual s1 = new Serie(titulo, genero, descripcion, anioPub, plataforma, duracion, loVi, puntuacion,
                rewiew);
        s1.setImgPath(imgPath);
        audioVisuales.add(s1);
        guardarCambios();
        registrarAudioVisual(s1);
        notifyChange();
        setMensaje("Agregado Correctamente");
        return new ResultadoValidacion(true, getMensaje());
    }
    private ResultadoValidacion agregarPelicula(String titulo, String genero,String descripcion, LocalDate anioPub,
                                                String plataforma, double duracion, boolean loVi, double puntuacion,
                                                String rewiew, String tipoPelStr, String imgPath){
        TipoPelicula tipoPel = TipoPelicula.convertirStr(tipoPelStr);
        AudioVisual p1 = new Pelicula(titulo, genero,descripcion, anioPub, plataforma, duracion,
                loVi, puntuacion, rewiew, tipoPel);
        p1.setImgPath(imgPath);
        audioVisuales.add(p1);
        guardarCambios();
        registrarAudioVisual(p1);
        notifyChange();
        setMensaje("Agregado correctamente.");
        return new ResultadoValidacion(true, getMensaje());

    }
    @Override
    public ResultadoValidacion editar(AudioVisual audioVisual,String titulo, String genero, String descripcion,
                                      LocalDate anioPub, String plataforma, double duracion, boolean loVi,
                                      double puntuacion, String rewiew, String tipo,
                                      String tipoPelStr, String imgPath) {
        if(audioVisual == null){
            return new ResultadoValidacion(false, "No hay elemento seleccionado");
        }
        audioVisual.setTitulo(titulo);
        audioVisual.setGenero(genero);
        audioVisual.setDescripcion(descripcion);
        audioVisual.setAnioPub(anioPub);
        audioVisual.setPlataforma(plataforma);
        audioVisual.setDuracion(duracion);
        audioVisual.setLoVi(loVi);
        audioVisual.setPuntuacion(puntuacion);
        audioVisual.setRewiew(rewiew);
        if(imgPath != null){
            audioVisual.setImgPath(imgPath);
        }
        if(audioVisual instanceof Pelicula){
            TipoPelicula tipoPel = TipoPelicula.convertirStr(tipoPelStr);
            ((Pelicula) audioVisual).setTipoPel(tipoPel);
        }
        guardarCambios();
        setMensaje("Se edito correctamente.");
        return new ResultadoValidacion(true, getMensaje());
    }

    @Override
    public void eliminar(AudioVisual audioVisualSeleccionado) {
        if(audioVisualSeleccionado == null){
            setMensaje("No hay nadie seleccionado.");
            return;
        }
        audioVisuales.remove(audioVisualSeleccionado);
        guardarCambios();
        registrarEliminacionAV(audioVisualSeleccionado);
        notifyChange();
        setMensaje("La serie/pelicula fue eliminada con exito.");
        if(audioVisualSelect == audioVisualSeleccionado){
            audioVisualSelect = null;
        }
    }
    @Override
    public void seleccionado(AudioVisual audioVisual) {
        this.audioVisualSelect = audioVisual;
    }
    @Override
    public boolean estaSeleccionado() {
        return audioVisualSelect != null;
    }
    @Override
    public AudioVisual getSeleccionado() {
        return audioVisualSelect;
    }

    //TEMPORADA
    @Override
    public void agregarTemporada(Serie serie){
        if(serie == null){
            setMensaje("La serie no existe o no fue seleccionada.");
            return;
        }
        Temporada t1 = serie.añadirTemp();
        guardarCambios();
        registrarTemporada(serie, t1);
        notifyChange();
        setMensaje("Temporada agregada correctamente.");
    }
    @Override
    public void eliminarTemp(Serie serie, Temporada temporada) {
        if(serie == null || temporada == null){
            setMensaje("la serie asosciada o temporada no existe para ser eliminada.");
            return;
        }
        serie.eliminarTemp(temporada);
        guardarCambios();
        setMensaje("La temporada fue eliminada correctamente.");
    }

    @Override
    public void seleccionadoTemp(Temporada temporada) {
        this.temporadaSelect = temporada;
    }

    @Override
    public Temporada getSeleccionadoTemp() {
        return temporadaSelect;
    }
    private Temporada buscarTempDelCap(Serie s, Capitulo c){
        for(Temporada t : s.getTemporadas()){
            if(t.getCapitulos().contains(c)){
              return  t;
            }
        }
        return  null;
    }
    //CAPITULO
    @Override
    public ResultadoValidacion agregarCapitulo(Temporada temporada, String titulo, String descripcion,
                                               double duracion, boolean visto){
        if(titulo == null || titulo.isBlank()){
            setMensaje("El titulo no puede estar vacio.");
            return new ResultadoValidacion(false, getMensaje());
        }
        if(descripcion == null || descripcion.isBlank()){
            setMensaje("La descripcion no puede estar vacia.");
            return new ResultadoValidacion(false,getMensaje());
        }
        if(duracion < 0.5){
            setMensaje("La duracion no puede ser menos a 5 seg.");
            return new ResultadoValidacion(false, getMensaje());
        }
        ResultadoValidacion res = null;
            Capitulo c1 = temporada.añadirCapitulo(titulo,descripcion, duracion, visto);
            Serie s1 = (Serie) getSeleccionado();
            guardarCambios();
            registrarCapitulo(s1, temporada, c1);
            notifyChange();
            setMensaje("El capitulo fue añadido correctamente.");
            res = new ResultadoValidacion(true, getMensaje());
        return  res;
    }
    @Override
    public ResultadoValidacion editarCap(Capitulo capitulo, String titulo, String descripcion, double duracion,
                                         boolean visto) {
        if (capitulo == null){
            return new ResultadoValidacion(false, "No hay elemento seleccionado.");
        }
        capitulo.setTituloCap(titulo);
        capitulo.setDescripcionCap(descripcion);
        capitulo.setVisto(visto);
        capitulo.setDuracionCap(duracion);
        guardarCambios();
        setMensaje("El capitulo fue editado correctamente.");
        return new ResultadoValidacion(true, getMensaje());
    }
    @Override
    public void eliminarCap(Temporada temporada, Capitulo capitulo) {
        if(temporada == null || capitulo == null){
            setMensaje("La temporada asociada o el capitulo seleecionado no existe para eliminarse.");
            return;
        }
        temporada.eliminarCapitulo(capitulo);
        Serie s1 = (Serie) getSeleccionado();
        guardarCambios();
        registrarEliminacionCap(s1, temporada, capitulo);
        notifyChange();
        setMensaje("El capitulo fue eliminado correctamente.");

    }
    @Override
    public void seleccionadoCap(Capitulo capitulo) {
        this.capituloSelect = capitulo;
    }
    @Override
    public boolean estaSeleccionadoCap() {
        return capituloSelect != null;
    }
    @Override
    public Capitulo getSeleccionadoCap() {
        return capituloSelect;
    }

    //BUSCAR
    @Override
    public boolean buscarInteligente(AudioVisual av, String filtro){
        if(filtro == null || filtro.isBlank()){
            return true;
        }
        return contine(av.getTitulo(),filtro) || contine(av.getGenero(),filtro) || contine(av.getPlataforma(), filtro)
                || contine(av.getTipo(),filtro);
    }
    private boolean contine(String campo, String filtro){
        return campo != null && campo.toLowerCase().contains(filtro);
    }

    //MENSAJE DE RESPUESTA
    @Override
    public String getMensaje() {
        return mensaje;
    }

    @Override
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    //TIPO DE FORMULARIO PARA LOS AGREGAR/EDITAR
    @Override
    public void setModoFormulario(ModoFormulario modoFormulario){
        this.modoFormulario = modoFormulario;
    }
    @Override
    public ModoFormulario getModoFormulario(){
        return modoFormulario;
    }
    //GETTERS
    @Override
    public ObservableList getAudioVisuales() {
        return audioVisuales;
    }
    @Override
    public List<Serie> getSeries(){
        List<Serie> resultado = new ArrayList<>();
        for(AudioVisual a1 : audioVisuales){
            if(a1 instanceof Serie){
                resultado.add((Serie) a1);
            }
        }
        return resultado;
    }
    @Override
    public List<Pelicula> getPeliculasRecientes(){
        return audioVisuales.stream().filter(a -> a instanceof Pelicula)
                .map(a -> (Pelicula) a).filter(Pelicula :: isLoVi).limit(5).toList();
    }
    @Override
    public ObservableList<Actividad> getActividades(){
        return actividades;
    }
    //REGISTRAR
    @Override
    public void registrarActividades(Actividad a){
        actividades.addFirst(a);
        if(actividades.size() > 15){
            actividades.removeLast();
        }
        actRepository.save(new ArrayList<>(actividades));
    }
    @Override
    public void registrarAudioVisual(AudioVisual av){
        String tipo = (av instanceof Serie) ? "Serie" : "Pelicula";
        String msj = "Se agregó la " + tipo + ": " + av.getTitulo();
        registrarActividades(new Actividad(msj, av.getTitulo()));
    }
    @Override
    public void registrarEliminacionAV(AudioVisual av){
        String tipo = (av instanceof Serie) ? "Serie" : "Pelicula";
        String msj = "Se eliminó la " + tipo + ": " + av.getTitulo();
        registrarActividades(new Actividad(msj, av.getTitulo()));
    }

    @Override
    public void registrarTemporada(Serie s, Temporada t) {
        String msj = "Se añadió la temporada " + t.getNumTemp() + " de la serie " + s.getTitulo();
        registrarActividades(new Actividad(msj, s.getTitulo(), t.getNumTemp(), null));
    }

    @Override
    public void registrarCapitulo(Serie s, Temporada t, Capitulo c) {
        String msj = "Se agregó el capitulo " + c.getNumCap() + " en la temporada " + t.getNumTemp() +
                " de la serie " + s.getTitulo();
        registrarActividades(new Actividad(msj, s.getTitulo(), t.getNumTemp(), c.getNumCap()));
    }

    @Override
    public void registrarEliminacionCap(Serie s, Temporada t, Capitulo c) {
        String msj = "Se eliminó el capitulo " + c.getNumCap() + " en la temporada " + t.getNumTemp() +
                " de la serie " + s.getTitulo();
        registrarActividades(new Actividad(msj, s.getTitulo(), t.getNumTemp(), c.getNumCap()));
    }
    //MARCAR COMO VISTO
    @Override
    public void marcarVisto(Serie s, Temporada t, Capitulo c){
        if(c == null || c.isVisto()){
            return;
        }
        c.setVisto(true);
        String msjC = "Se vio el capitulo " + c.getNumCap() + " (T" + t.getNumTemp() + ") de la serie " +
                s.getTitulo();
        registrarActividades(new Actividad(msjC,s.getTitulo(), t.getNumTemp(), c.getNumCap()));
        notifyChange();
        String msjTemp = "Se completo la temporada " + t.getNumTemp() + " de la serie " + s.getTitulo();
        if(t.estaCompletaTemp()){
            registrarActividades(new Actividad(msjTemp, s.getTitulo(), t.getNumTemp(), null));
            notifyChange();
        }
        String msjSerie = "Se completo la serie " + s.getTitulo();
        if(s.estaCompleta()){
            s.setLoVi(true);
            registrarActividades(new Actividad(msjSerie, s.getTitulo(), null, null));
            notifyChange();
        }
        guardarCambios();
    }
    @Override
    public ObservableIntegerValue version(){
        return version;
    }
    public void notifyChange(){
        version.set(version().get() + 1);
    }
}
