package com.recolectarq.campo.recolectarq.Modelo;

public class Perfiles {

    private int pefil_id;
    private String descripcion;

    public Perfiles() {
    }

    public Perfiles(int pefil_id, String descripcion) {
        this.pefil_id = pefil_id;
        this.descripcion = descripcion;
    }

    public int getPefil_id() {
        return pefil_id;
    }

    public void setPefil_id(int pefil_id) {
        this.pefil_id = pefil_id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
