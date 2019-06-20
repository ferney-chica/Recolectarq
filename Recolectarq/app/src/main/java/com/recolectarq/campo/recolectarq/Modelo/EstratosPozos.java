package com.recolectarq.campo.recolectarq.Modelo;

public class EstratosPozos {
    private int estrato_pozo_id;
    private int texturas_estratos_textura_estrato_id;
    private int tipos_estratos_tipo_estrato_id;
    private int estructuras_estratos_estructura_estrato_id;
    private int pozos_pozo_id;
    private float profundidad;
    private String color;
    private String observacion;

    public EstratosPozos() {
    }

    public EstratosPozos(int estrato_pozo_id, int texturas_estratos_textura_estrato_id, int tipos_estratos_tipo_estrato_id,
                         int estructuras_estratos_estructura_estrato_id, int pozos_pozo_id, float profundidad, String color,
                         String observacion) {
        this.estrato_pozo_id = estrato_pozo_id;
        this.texturas_estratos_textura_estrato_id = texturas_estratos_textura_estrato_id;
        this.tipos_estratos_tipo_estrato_id = tipos_estratos_tipo_estrato_id;
        this.estructuras_estratos_estructura_estrato_id = estructuras_estratos_estructura_estrato_id;
        this.pozos_pozo_id = pozos_pozo_id;
        this.profundidad = profundidad;
        this.color = color;
        this.observacion = observacion;
    }

    public int getEstrato_pozo_id() {
        return estrato_pozo_id;
    }

    public void setEstrato_pozo_id(int estrato_pozo_id) {
        this.estrato_pozo_id = estrato_pozo_id;
    }

    public int getTexturas_estratos_textura_estrato_id() {
        return texturas_estratos_textura_estrato_id;
    }

    public void setTexturas_estratos_textura_estrato_id(int texturas_estratos_textura_estrato_id) {
        this.texturas_estratos_textura_estrato_id = texturas_estratos_textura_estrato_id;
    }

    public int getTipos_estratos_tipo_estrato_id() {
        return tipos_estratos_tipo_estrato_id;
    }

    public void setTipos_estratos_tipo_estrato_id(int tipos_estratos_tipo_estrato_id) {
        this.tipos_estratos_tipo_estrato_id = tipos_estratos_tipo_estrato_id;
    }

    public int getEstructuras_estratos_estructura_estrato_id() {
        return estructuras_estratos_estructura_estrato_id;
    }

    public void setEstructuras_estratos_estructura_estrato_id(int estructuras_estratos_estructura_estrato_id) {
        this.estructuras_estratos_estructura_estrato_id = estructuras_estratos_estructura_estrato_id;
    }

    public int getPozos_pozo_id() {
        return pozos_pozo_id;
    }

    public void setPozos_pozo_id(int pozos_pozo_id) {
        this.pozos_pozo_id = pozos_pozo_id;
    }

    public float getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(float profundidad) {
        this.profundidad = profundidad;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
