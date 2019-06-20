package com.recolectarq.campo.recolectarq.Modelo;

public class Geoforma {

    private int id;

    public Geoforma(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    private String nombre;

    public Geoforma() {
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
