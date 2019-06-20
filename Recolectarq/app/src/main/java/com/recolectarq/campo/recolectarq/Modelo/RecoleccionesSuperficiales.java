package com.recolectarq.campo.recolectarq.Modelo;

import java.io.Serializable;

public class RecoleccionesSuperficiales implements Serializable {

    private int recoleccion_superficial_id;
    private int umtp_id;
    private String descripcion;
    private int usuario_creador;
    private int usuario_encargado;
    private String codigo_rotulo;

    public RecoleccionesSuperficiales() {
    }

    public RecoleccionesSuperficiales(int recoleccion_superficial_id, int umtp_id, String descripcion, int usuario_creador, int usuario_encargado, String codigo_rotulo) {
        this.recoleccion_superficial_id = recoleccion_superficial_id;
        this.umtp_id = umtp_id;
        this.descripcion = descripcion;
        this.usuario_creador = usuario_creador;
        this.usuario_encargado = usuario_encargado;
        this.codigo_rotulo = codigo_rotulo;
    }

    public int getrecoleccion_superficial_id() {
        return recoleccion_superficial_id;
    }

    public void setrecoleccion_superficial_id(int recoleccion_superficial_id) {
        this.recoleccion_superficial_id = recoleccion_superficial_id;
    }

    public int getUmtp_id() {
        return umtp_id;
    }

    public void setUmtp_id(int umtp_id) {
        this.umtp_id = umtp_id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getUsuario_creador() {
        return usuario_creador;
    }

    public void setUsuario_creador(int usuario_creador) {
        this.usuario_creador = usuario_creador;
    }

    public int getUsuario_encargado() {
        return usuario_encargado;
    }

    public void setUsuario_encargado(int usuario_encargado) {
        this.usuario_encargado = usuario_encargado;
    }

    public String getCodigo_rotulo() {
        return codigo_rotulo;
    }

    public void setCodigo_rotulo(String codigo_rotulo) {
        this.codigo_rotulo = codigo_rotulo;
    }
}
