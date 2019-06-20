package com.recolectarq.campo.recolectarq.Modelo;

public class EstructurasEstratos {

    private int estructura_estrato_id;
    private String nombre;

    public EstructurasEstratos() {
    }

    public EstructurasEstratos(int estructura_estrato_id, String nombre) {
        this.estructura_estrato_id = estructura_estrato_id;
        this.nombre = nombre;
    }

    public int getEstructura_estrato_id() {
        return estructura_estrato_id;
    }

    public void setEstructura_estrato_id(int estructura_estrato_id) {
        this.estructura_estrato_id = estructura_estrato_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
