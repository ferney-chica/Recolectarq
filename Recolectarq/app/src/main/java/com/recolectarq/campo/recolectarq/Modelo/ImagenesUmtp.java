package com.recolectarq.campo.recolectarq.Modelo;

import android.graphics.Bitmap;

public class ImagenesUmtp {
    private int id;
    private int umtp_id;
    private String tipo;
    private String nombre;



    private Bitmap imagenUmtp;

    public ImagenesUmtp() {
    }

    public ImagenesUmtp(int id, int umtp_id, String tipo, String nombre) {
        this.id = id;
        this.umtp_id = umtp_id;
        this.tipo = tipo;
        this.nombre = nombre;
    }

    public Bitmap getImagenUmtp() {
        return imagenUmtp;
    }

    public void setImagenUmtp(Bitmap imagenUmtp) {
        this.imagenUmtp = imagenUmtp;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUmtp_id() {
        return umtp_id;
    }

    public void setUmtp_id(int umtp_id) {
        this.umtp_id = umtp_id;
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
