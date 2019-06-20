package com.recolectarq.campo.recolectarq.Modelo;

import java.io.Serializable;

public class NivelesPozos implements Serializable {

    private int nivel_pozo_id;
    private int pozos_pozo_id;
    private int numero;
    private int profundidad;
    private String codigo_rotulo;

    public NivelesPozos() {
    }

    public NivelesPozos(int nivel_pozo_id, int pozos_pozo_id, int numero, int profundidad, String codigo_rotulo) {
        this.nivel_pozo_id = nivel_pozo_id;
        this.pozos_pozo_id = pozos_pozo_id;
        this.numero = numero;
        this.profundidad = profundidad;
        this.codigo_rotulo = codigo_rotulo;
    }

    public int getNivel_pozo_id() {
        return nivel_pozo_id;
    }

    public void setNivel_pozo_id(int nivel_pozo_id) {
        this.nivel_pozo_id = nivel_pozo_id;
    }

    public int getPozos_pozo_id() {
        return pozos_pozo_id;
    }

    public void setPozos_pozo_id(int pozos_pozo_id) {
        this.pozos_pozo_id = pozos_pozo_id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }

    public String getCodigo_rotulo() {
        return codigo_rotulo;
    }

    public void setCodigo_rotulo(String codigo_rotulo) {
        this.codigo_rotulo = codigo_rotulo;
    }
}
