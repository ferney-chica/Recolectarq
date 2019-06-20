package com.recolectarq.campo.recolectarq.Modelo;

public class RegimenPropiedad {
    private int id;
    private String nombre;

    public RegimenPropiedad() {
    }

    public RegimenPropiedad(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
