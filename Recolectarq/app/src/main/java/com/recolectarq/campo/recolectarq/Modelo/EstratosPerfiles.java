package com.recolectarq.campo.recolectarq.Modelo;

import java.io.Serializable;

public class EstratosPerfiles implements Serializable {

    private int estrato_perfil_id;
    private int texturas_estratos_textura_estrato_id;
    private String textura_estrato_nombre;
    private int estructuras_estratos_estructura_estrato_id;
    private String estructura_estrato_nombre;
    private int tipos_estratos_tipo_estrato_id;
    private String tipo_estrato_nombre;
    private int perfiles_expuestos_perfil_expuesto_id;
    private float profundidad;
    private String color;
    private String observacion;
    private String codigo_rotulo;

    public EstratosPerfiles() {
    }

    public EstratosPerfiles(int estrato_perfil_id, int texturas_estratos_textura_estrato_id, String textura_estrato_nombre,
                            int estructuras_estratos_estructura_estrato_id, String estructura_estrato_nombre,
                            int tipos_estratos_tipo_estrato_id, String tipo_estrato_nombre,
                            int perfiles_expuestos_perfil_expuesto_id, float profundidad, String color, String observacion,
                            String codigo_rotulo) {
        this.estrato_perfil_id = estrato_perfil_id;
        this.texturas_estratos_textura_estrato_id = texturas_estratos_textura_estrato_id;
        this.textura_estrato_nombre = textura_estrato_nombre;
        this.estructuras_estratos_estructura_estrato_id = estructuras_estratos_estructura_estrato_id;
        this.estructura_estrato_nombre = estructura_estrato_nombre;
        this.tipos_estratos_tipo_estrato_id = tipos_estratos_tipo_estrato_id;
        this.tipo_estrato_nombre = tipo_estrato_nombre;
        this.perfiles_expuestos_perfil_expuesto_id = perfiles_expuestos_perfil_expuesto_id;
        this.profundidad = profundidad;
        this.color = color;
        this.observacion = observacion;
        this.codigo_rotulo = codigo_rotulo;
    }

    public int getEstrato_perfil_id() {
        return estrato_perfil_id;
    }

    public void setEstrato_perfil_id(int estrato_perfil_id) {
        this.estrato_perfil_id = estrato_perfil_id;
    }

    public int getTexturas_estratos_textura_estrato_id() {
        return texturas_estratos_textura_estrato_id;
    }

    public void setTexturas_estratos_textura_estrato_id(int texturas_estratos_textura_estrato_id) {
        this.texturas_estratos_textura_estrato_id = texturas_estratos_textura_estrato_id;
    }

    public String getTextura_estrato_nombre() {
        return textura_estrato_nombre;
    }

    public void setTextura_estrato_nombre(String textura_estrato_nombre) {
        this.textura_estrato_nombre = textura_estrato_nombre;
    }

    public int getEstructuras_estratos_estructura_estrato_id() {
        return estructuras_estratos_estructura_estrato_id;
    }

    public void setEstructuras_estratos_estructura_estrato_id(int estructuras_estratos_estructura_estrato_id) {
        this.estructuras_estratos_estructura_estrato_id = estructuras_estratos_estructura_estrato_id;
    }

    public String getEstructura_estrato_nombre() {
        return estructura_estrato_nombre;
    }

    public void setEstructura_estrato_nombre(String estructura_estrato_nombre) {
        this.estructura_estrato_nombre = estructura_estrato_nombre;
    }

    public int getTipos_estratos_tipo_estrato_id() {
        return tipos_estratos_tipo_estrato_id;
    }

    public void setTipos_estratos_tipo_estrato_id(int tipos_estratos_tipo_estrato_id) {
        this.tipos_estratos_tipo_estrato_id = tipos_estratos_tipo_estrato_id;
    }

    public String getTipo_estrato_nombre() {
        return tipo_estrato_nombre;
    }

    public void setTipo_estrato_nombre(String tipo_estrato_nombre) {
        this.tipo_estrato_nombre = tipo_estrato_nombre;
    }

    public int getPerfiles_expuestos_perfil_expuesto_id() {
        return perfiles_expuestos_perfil_expuesto_id;
    }

    public void setPerfiles_expuestos_perfil_expuesto_id(int perfiles_expuestos_perfil_expuesto_id) {
        this.perfiles_expuestos_perfil_expuesto_id = perfiles_expuestos_perfil_expuesto_id;
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

    public String getCodigo_rotulo() {
        return codigo_rotulo;
    }

    public void setCodigo_rotulo(String codigo_rotulo) {
        this.codigo_rotulo = codigo_rotulo;
    }
}
