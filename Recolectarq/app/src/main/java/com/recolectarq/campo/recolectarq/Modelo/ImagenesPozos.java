package com.recolectarq.campo.recolectarq.Modelo;

public class ImagenesPozos {

    private int imagen_pozo_id;
    private int pozos_pozo_id;
    private String nombre;
    private String tipo;

    public ImagenesPozos() {
    }

    public ImagenesPozos(int imagen_pozo_id, int pozos_pozo_id, String nombre, String tipo) {
        this.imagen_pozo_id = imagen_pozo_id;
        this.pozos_pozo_id = pozos_pozo_id;
        this.nombre = nombre;
        this.tipo = tipo;
    }


    public int getImagen_pozo_id() {
        return imagen_pozo_id;
    }

    public void setImagen_pozo_id(int imagen_pozo_id) {
        this.imagen_pozo_id = imagen_pozo_id;
    }

    public int getPozos_pozo_id() {
        return pozos_pozo_id;
    }

    public void setPozos_pozo_id(int pozos_pozo_id) {
        this.pozos_pozo_id = pozos_pozo_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

