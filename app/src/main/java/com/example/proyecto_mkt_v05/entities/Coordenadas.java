package com.example.proyecto_mkt_v05.entities;

import java.io.Serializable;

public class Coordenadas implements Serializable {

    private double latitud, longitud;

    public Coordenadas() {
    }

    public Coordenadas(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
