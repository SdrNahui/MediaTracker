package com.example.mediatracker.Model;

public enum TipoPelicula {
    ORIGINAL ("Original"),
    REMAKE ("Remake"),
    PRECUELA ("Precuela"),
    SECUELA ("Secuela"),
    SPIN_OFF ("Spin-off"),
    LIVE_ACTION ("Live Action");
    private final String texto;
    TipoPelicula(String texto){
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public static TipoPelicula convertirStr(String text) {
        for (TipoPelicula t : values()) {
            if (t.texto.equalsIgnoreCase(text)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Tipo de pelicula invalido: " + text);
    }
}
