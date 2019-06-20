package com.recolectarq.campo.recolectarq.Modelo;

public class DedicacionesEntornos {

    private int id;
    private String nombre;

    public DedicacionesEntornos(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public DedicacionesEntornos() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
