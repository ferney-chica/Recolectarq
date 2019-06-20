package com.recolectarq.campo.recolectarq.Modelo;

import android.graphics.Bitmap;

public class Imagenes {
    private int id;
    private int intervencion_id;
    private String nombre;
    private String tipo;
    private Bitmap imagen;

    public Imagenes() {
    }

    public Imagenes(int id, int intervencion_id, String nombre ,String tipo) {
        this.id = id;
        this.intervencion_id = intervencion_id;
        this.tipo = tipo;
        this.nombre = nombre;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIntervencion_id() {
        return intervencion_id;
    }

    public void setIntervencion_id(int intervencion_id) {
        this.intervencion_id = intervencion_id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
