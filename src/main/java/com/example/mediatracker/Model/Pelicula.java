package com.example.mediatracker.Model;

import java.time.LocalDate;

public class Pelicula extends AudioVisual {
    private TipoPelicula tipoPel;

    protected Pelicula(){
        this.tipo = "pelicula";
    }
    public Pelicula(String titulo, String genero, String descripcion, LocalDate anioPub, String plataforma,
                    double duracion, boolean loVi, double puntuacion, String rewiew, TipoPelicula tipoPel) {
        super(titulo, genero, descripcion, anioPub, plataforma, duracion, loVi, puntuacion, rewiew);
      /*  if((tipo != "precuela") || (tipo != "secuela") || (tipo != "original")){
            throw new RuntimeException("Solo puede ser precuela, secuela u original");
        } */
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
