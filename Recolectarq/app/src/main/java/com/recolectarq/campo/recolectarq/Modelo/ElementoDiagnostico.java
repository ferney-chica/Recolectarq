package com.recolectarq.campo.recolectarq.Modelo;

import java.io.Serializable;

public class ElementoDiagnostico implements Serializable {

    private int idIntervencion;
    private String tipoElementoDiagnostico;//Para material tipoMaterial, para estructura tipo estructura
    private int cantidad;
    private String descripcion;
    private String tipo; //E:estructura --- M:material

    public ElementoDiagnostico() {
    }

    public ElementoDiagnostico(int idIntervencion, String tipoElementoDiagnostico, int cantidad, String descripcion, String tipo) {
        this.idIntervencion = idIntervencion;
        this.tipoElementoDiagnostico = tipoElementoDiagnostico;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public int getIdIntervencion() {
        return idIntervencion;
    }

    public void setIdIntervencion(int idIntervencion) {
        this.idIntervencion = idIntervencion;
    }

    public String getTipoElementoDiagnostico() {
        return tipoElementoDiagnostico;
    }

    public void setTipoElementoDiagnostico(String tipoElementoDiagnostico) {
        this.tipoElementoDiagnostico = tipoElementoDiagnostico;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
