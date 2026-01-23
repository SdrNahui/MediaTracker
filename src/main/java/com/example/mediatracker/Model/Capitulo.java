package com.example.mediatracker.Model;

public class Capitulo {
    private int numCap;
    private String tituloCap;
    private String descripcionCap;
    private boolean visto;
    private double duracionCap;

    public Capitulo(String tituloCap, String descripcionCap, double duracionCap, boolean visto,
                    int numCap){
        if(tituloCap == null || tituloCap.isBlank()){
            throw new RuntimeException("El titulo no puede ser vacio");
        }
        this.tituloCap = tituloCap;
        if (duracionCap < 0.5){
            throw new RuntimeException("La duracion no puede ser menor a 5 segundos");
        }
        this.descripcionCap = descripcionCap;
        this.visto = visto;
        this.duracionCap = duracionCap;
        this.numCap = numCap;
    }
    public String getTituloCap(){
        return  tituloCap;
    }
    public String getDescripcionCap(){
        return descripcionCap;
    }

    public boolean isVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
    }

    public double getDuracionCap(){
        return duracionCap;
    }
    public int getNumCap(){
        return  numCap;
    }
    public void setTituloCap(String tituloCap){
        this.tituloCap =tituloCap;
    }

    public void setDescripcionCap(String descripcionCap) {
        this.descripcionCap = descripcionCap;
    }

    public void setDuracionCap(double duracionCap) {
        this.duracionCap = duracionCap;
    }
    public void setNumCap(int numCap){
        this.numCap = numCap;
    }

}
