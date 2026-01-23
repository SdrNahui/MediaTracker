package com.example.mediatracker.Model;

import java.time.LocalDate;

public class Pelicula extends AudioVisual {
    private TipoPelicula tipoPel;
    public Pelicula(String titulo, String genero, String descripcion, LocalDate anioPub, String plataforma,
                    double duracion, boolean loVi, double puntuacion, String rewiew, TipoPelicula tipoPel) {
        super(titulo, genero, descripcion, anioPub, plataforma, duracion, loVi, puntuacion, rewiew);
        this.tipoPel = tipoPel;
        this.tipo = "pelicula";
        this.imgPath = null;
    }
    public TipoPelicula getTipoPel(){
        return tipoPel;
    }
    public void setTipoPel(TipoPelicula tipoPel) {
        this.tipoPel = tipoPel;
    }
}
