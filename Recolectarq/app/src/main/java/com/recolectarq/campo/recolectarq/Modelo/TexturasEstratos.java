package com.recolectarq.campo.recolectarq.Modelo;

public class TexturasEstratos {

    private int textura_estrato_id;
    private String nombre;

    public TexturasEstratos() {
    }

    public TexturasEstratos(int textura_estrato_id, String nombre) {
        this.textura_estrato_id = textura_estrato_id;
        this.nombre = nombre;
    }

    public int getTextura_estrato_id() {
        return textura_estrato_id;
    }

    public void setTextura_estrato_id(int textura_estrato_id) {
        this.textura_estrato_id = textura_estrato_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
