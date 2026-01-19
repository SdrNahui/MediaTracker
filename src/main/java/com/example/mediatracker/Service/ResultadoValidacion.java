package com.example.mediatracker.Service;

public class ResultadoValidacion {
    private final boolean ok;
    private final String msj;

    public ResultadoValidacion(boolean ok, String msj) {
        this.ok = ok;
        this.msj = msj;
    }

    public boolean isOk() {
        return ok;
    }

    public String getMsj() {
        return msj;
    }
}
