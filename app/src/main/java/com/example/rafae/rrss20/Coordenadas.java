package com.example.rafae.rrss20;

/**
 * Created by rafae on 4/1/2017.
 */

public class Coordenadas {

    public static String latitud;
    public static String longitud;

    public Coordenadas(Object f ,String latitud, String longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Coordenadas() {
    }

    public  String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        Coordenadas.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public  void setLongitud(String longitud) {
        Coordenadas.longitud = longitud;
    }
}
