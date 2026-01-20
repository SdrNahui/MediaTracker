package com.example.mediatracker.Model;

import java.time.LocalDate;

public abstract class AudioVisual {
    private String titulo;
    private String genero;
    protected String tipo;
    private String descripcion;
    private LocalDate anioPub;
    private String plataforma;
    private double duracion;
    private boolean loVi;
    private double puntuacion;
    private String rewiew;
    private LocalDate agregado;
    protected String imgPath;
    public AudioVisual(){

    }
    public AudioVisual(String titulo, String genero, String descripcion, LocalDate anioPub, String plataforma,
                       double duracion, boolean loVi, double puntuacion, String rewiew){
        if(titulo == null || titulo.isBlank()) {
            throw new RuntimeException("El nombre no puede estar vacio");
        };
        this.titulo = titulo;
        if(genero == null || genero.isBlank()) {
            throw new RuntimeException("El genero no puede estar vacio");
        };
        this.genero = genero;
        this.descripcion = descripcion;
        this.anioPub = anioPub;
        if(plataforma == null || plataforma.isBlank()){
            throw new RuntimeException("La plataforma no puede ser vacia");
        }
        this.plataforma = plataforma;
        if(duracion < 0) {
            throw new RuntimeException("Debe al menoso tener 5 segundos");
        }
        this.duracion = duracion;
        this.loVi = loVi;
        if(puntuacion <= 0.0 || puntuacion >= 10.0){
            throw  new RuntimeException("La puntuacion no puede ser menor a 1.0 ni mayor a 10.0");
        }
        this.puntuacion = puntuacion;
        this.rewiew = rewiew;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public LocalDate getAnioPub() {
        return anioPub;
    }

    public double getDuracion() {
        return duracion;
    }

    public boolean isLoVi() {
        return loVi;
    }

    public double getPuntuacion() {
        return puntuacion;
    }
    public String getTipo(){
        return tipo;
    }

    public String getRewiew() {
        if(rewiew == null){
            return "";
        }
        return rewiew;
    }
    public String getImgPath(){
        return imgPath;
    }

    public LocalDate getAgregado() {
        return agregado;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setAnioPub(LocalDate anioPub) {
        this.anioPub = anioPub;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public void setLoVi(boolean loVi) {
        this.loVi = loVi;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void setRewiew(String rewiew) {
        this.rewiew = rewiew;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(titulo).append("\n").append(genero).append("\n").append(descripcion).append("\n").append(plataforma);
        return sb.toString();
    }

}
