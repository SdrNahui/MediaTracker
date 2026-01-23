package com.example.mediatracker.Model;

import java.util.ArrayList;
import java.util.List;

public class Temporada {
    private int numTemp;
    private List<Capitulo> capitulos;

    public Temporada(int numTemp){
        this.numTemp = numTemp;
        this.capitulos = new  ArrayList<>();
    }

    public Capitulo añadirCapitulo(String titulo,String descripcion, double duracion,boolean visto) {
        int num = capitulos.size() + 1;
        Capitulo c1 = new Capitulo(titulo,descripcion, duracion, visto, num);
        capitulos.add(c1);
        return c1;
    }
    public void eliminarCapitulo(Capitulo capitulo){
        if(capitulo == null){
            return;
        }
        capitulos.remove(capitulo);
        for(int i = 0; i < capitulos.size(); i++){
            capitulos.get(i).setNumCap(i + 1);
        }
    }
    public boolean estaCompletaTemp(){
        boolean ret = true;
        if(capitulos.isEmpty()){
            return false;
        }
        for (Capitulo c : capitulos){
            ret &= c.isVisto();

        }
        return ret;
    }

    public double getDuracion(){
        return capitulos.stream().mapToDouble(Capitulo::getDuracionCap).sum();
    }
    public int getNumTemp(){
        return numTemp;
    }
    public List<Capitulo> getCapitulos(){
        return capitulos;
    }
    public void setNumTemp(int numTemp){
        this.numTemp = numTemp;
    }

}
