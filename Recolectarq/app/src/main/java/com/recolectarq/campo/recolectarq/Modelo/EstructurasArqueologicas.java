package com.recolectarq.campo.recolectarq.Modelo;

import java.io.Serializable;

public class EstructurasArqueologicas implements Serializable {

    private int estructuras_arqueologicas_id;
    private int tipos_estructuras_id;
    private String tipos_estructuras_nombre;
    private int topologias_estructuras_id;
    private String topologias_estructuras_nombre;
    private int perfiles_expuestos_perfil_expuesto_id;
    private int recolecciones_superficiales_recoleccion_superficial_id;
    private int pozos_pozo_id;
    private String descripcion;
    private String punto_conexo;
    private float utmx;
    private float utmy;
    private String latitud;
    private String longitud;
    private String dimension;
    private String entorno;
    private String intervencion;

    public EstructurasArqueologicas() {
    }

    public EstructurasArqueologicas(int estructuras_arqueologicas_id, int tipos_estructuras_id, String tipos_estructuras_nombre,
                                    int topologias_estructuras_id, String topologias_estructuras_nombre,
                                    int perfiles_expuestos_perfil_expuesto_id, int recolecciones_superficiales_recoleccion_superficial_id,
                                    int pozos_pozo_id, String descripcion, String punto_conexo, float utmx, float utmy, String latitud, String longitud,
                                    String dimension, String entorno, String intervencion) {

        this.estructuras_arqueologicas_id = estructuras_arqueologicas_id;
        this.tipos_estructuras_id = tipos_estructuras_id;
        this.tipos_estructuras_nombre = tipos_estructuras_nombre;
        this.topologias_estructuras_id = topologias_estructuras_id;
        this.topologias_estructuras_nombre = topologias_estructuras_nombre;
        this.perfiles_expuestos_perfil_expuesto_id = perfiles_expuestos_perfil_expuesto_id;
        this.recolecciones_superficiales_recoleccion_superficial_id = recolecciones_superficiales_recoleccion_superficial_id;
        this.pozos_pozo_id = pozos_pozo_id;
        this.descripcion = descripcion;
        this.punto_conexo = punto_conexo;
        this.utmx = utmx;
        this.utmy = utmy;
        this.latitud = latitud;
        this.longitud = longitud;
        this.dimension = dimension;
        this.entorno = entorno;
        this.intervencion = intervencion;
    }

    public int getEstructuras_arqueologicas_id() {
        return estructuras_arqueologicas_id;
    }

    public void setEstructuras_arqueologicas_id(int estructuras_arqueologicas_id) {
        this.estructuras_arqueologicas_id = estructuras_arqueologicas_id;
    }

    public int getTipos_estructuras_id() {
        return tipos_estructuras_id;
    }

    public void setTipos_estructuras_id(int tipos_estructuras_id) {
        this.tipos_estructuras_id = tipos_estructuras_id;
    }

    public String getTipos_estructuras_nombre() {
        return tipos_estructuras_nombre;
    }

    public void setTipos_estructuras_nombre(String tipos_estructuras_nombre) {
        this.tipos_estructuras_nombre = tipos_estructuras_nombre;
    }

    public int getTopologias_estructuras_id() {
        return topologias_estructuras_id;
    }

    public void setTopologias_estructuras_id(int topologias_estructuras_id) {
        this.topologias_estructuras_id = topologias_estructuras_id;
    }

    public String getTopologias_estructuras_nombre() {
        return topologias_estructuras_nombre;
    }

    public void setTopologias_estructuras_nombre(String topologias_estructuras_nombre) {
        this.topologias_estructuras_nombre = topologias_estructuras_nombre;
    }

    public int getPerfiles_expuestos_perfil_expuesto_id() {
        return perfiles_expuestos_perfil_expuesto_id;
    }

    public void setPerfiles_expuestos_perfil_expuesto_id(int perfiles_expuestos_perfil_expuesto_id) {
        this.perfiles_expuestos_perfil_expuesto_id = perfiles_expuestos_perfil_expuesto_id;
    }

    public int getRecolecciones_superficiales_recoleccion_superficial_id() {
        return recolecciones_superficiales_recoleccion_superficial_id;
    }

    public void setRecolecciones_superficiales_recoleccion_superficial_id(int recolecciones_superficiales_recoleccion_superficial_id) {
        this.recolecciones_superficiales_recoleccion_superficial_id = recolecciones_superficiales_recoleccion_superficial_id;
    }

    public int getPozos_pozo_id() {
        return pozos_pozo_id;
    }

    public void setPozos_pozo_id(int pozos_pozo_id) {
        this.pozos_pozo_id = pozos_pozo_id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPunto_conexo() {
        return punto_conexo;
    }

    public void setPunto_conexo(String punto_conexo) {
        this.punto_conexo = punto_conexo;
    }

    public float getUtmx() {
        return utmx;
    }

    public void setUtmx(float utmx) {
        this.utmx = utmx;
    }

    public float getUtmy() {
        return utmy;
    }

    public void setUtmy(float utmy) {
        this.utmy = utmy;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getEntorno() {
        return entorno;
    }

    public void setEntorno(String entorno) {
        this.entorno = entorno;
    }

    public String getIntervencion() {
        return intervencion;
    }

    public void setIntervencion(String intervencion) {
        this.intervencion = intervencion;
    }
}
