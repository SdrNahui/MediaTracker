package com.example.mediatracker.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Actividad {
    private String msj;
    private String refTitulo;
    private Integer numTemp;
    private Integer numCap;
    private LocalDate fecha;

    public Actividad(String msj, String refTitulo, Integer numTemp, Integer numCap){
        this.msj = msj;
        this.refTitulo = refTitulo;
        this.numTemp = numTemp;
        this.numCap = numCap;
        this.fecha = LocalDate.now();
    }
    public Actividad(String msj, String refTitulo){
        this.msj = msj;
        this.refTitulo = refTitulo;
        this.numCap = null;
        this.numTemp = null;
        this.fecha = LocalDate.now();
    }

    public Integer getNumCap() {
        return numCap;
    }

    public Integer getNumTemp() {
        return numTemp;
    }

    public String getTitulo() {
        return refTitulo;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    public String getMsj(){
        return msj;
    }
}
