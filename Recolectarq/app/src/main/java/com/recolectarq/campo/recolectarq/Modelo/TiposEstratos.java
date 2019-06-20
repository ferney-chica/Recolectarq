package com.recolectarq.campo.recolectarq.Modelo;

public class TiposEstratos {

    private int tipo_estrato_id;
    private String nombre;

    public TiposEstratos() {
    }

    public TiposEstratos(int tipo_estrato_id, String nombre) {
        this.tipo_estrato_id = tipo_estrato_id;
        this.nombre = nombre;
    }

    public int getTipo_estrato_id() {
        return tipo_estrato_id;
    }

    public void setTipo_estrato_id(int tipo_estrato_id) {
        this.tipo_estrato_id = tipo_estrato_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
