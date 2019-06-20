package com.recolectarq.campo.recolectarq.Modelo;

public class LinealesVisibilidades {
    private int id;
    private String nombre;

    public LinealesVisibilidades() {
    }

    public LinealesVisibilidades(int id, String nombre) {
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
