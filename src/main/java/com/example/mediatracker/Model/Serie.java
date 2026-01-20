package com.example.mediatracker.Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Serie extends AudioVisual {
    private ArrayList<Temporada> temporadas;
    private int totalCapitulos;

    protected Serie(){
        this.tipo = "serie";
    }
    public Serie(String titulo, String genero, String descripcion, LocalDate anioPub, String plataforma,
                 double duracion, boolean loVi, double puntuacion, String rewiew) {
        super(titulo, genero,descripcion, anioPub, plataforma, duracion, loVi, puntuacion, rewiew);
        this.temporadas = new ArrayList<>();
        this.totalCapitulos = 0;
        this.tipo = "serie";
        this.imgPath = null;

    }

    @Override
    public double getDuracion() {
        return temporadas.stream().mapToDouble(Temporada :: getDuracion).sum();
    }

    public Temporada añadirTemp(){
        int num = temporadas.size() + 1;
        Temporada t1 = new Temporada(num);
        temporadas.add(t1);
        return t1;
    }
    public ArrayList<Temporada> getTemporadas(){
        return temporadas;
    }
    public void eliminarTemp(Temporada temporada){
        if(temporada == null){
            return;
        }
        temporadas.remove(temporada);
        for(int i = 0; i < temporadas.size(); i++){
            temporadas.get(i).setNumTemp(i + 1);
        }

    }
    public boolean estaCompleta(){
        boolean ret = true;
        for(Temporada t : temporadas){
            ret &= t.estaCompletaTemp();
        }
        return ret;
    }


    public String getCantidadCapitulos() {
        return String.valueOf(totalCapitulos);
    }
}
