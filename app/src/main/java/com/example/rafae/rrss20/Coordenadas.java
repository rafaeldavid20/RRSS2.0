package com.example.rafae.rrss20;

/**
 * Created by rafae on 4/1/2017.
 */

public class Coordenadas {

    public static float latitud;
    public static float longitud;

    public Coordenadas(Object f ,float latitud, float longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Coordenadas() {
    }

    public  float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        Coordenadas.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public  void setLongitud(float longitud) {
        Coordenadas.longitud = longitud;
    }
}
