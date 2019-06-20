package com.recolectarq.campo.recolectarq.Modelo;

public class Muestras {

    private int muestra_id;
    private int tipos_muestras_id;
    private String tipos_muestras_nombre;
    private int tipos_materiales_id;
    private String tipos_materiales_nombre;
    private int perfiles_expuestos_perfil_expuesto_id;
    private int recolecciones_superficiales_recoleccion_superficial_id;
    private int pozos_pozo_id;
    private String argumentacion;
    private String destino;
    private String contexto;
    private String intervencion;

    public Muestras() {
    }

    public Muestras(int muestra_id, int tipos_muestras_id, String tipos_muestras_nombre, int tipos_materiales_id,
                    String tipos_materiales_nombre, int perfiles_expuestos_perfil_expuesto_id,
                    int recolecciones_superficiales_recoleccion_superficial_id, int pozos_pozo_id, String argumentacion,
                    String destino, String contexto, String intervencion) {
        this.muestra_id = muestra_id;
        this.tipos_muestras_id = tipos_muestras_id;
        this.tipos_muestras_nombre = tipos_muestras_nombre;
        this.tipos_materiales_id = tipos_materiales_id;
        this.tipos_materiales_nombre = tipos_materiales_nombre;
        this.perfiles_expuestos_perfil_expuesto_id = perfiles_expuestos_perfil_expuesto_id;
        this.recolecciones_superficiales_recoleccion_superficial_id = recolecciones_superficiales_recoleccion_superficial_id;
        this.pozos_pozo_id = pozos_pozo_id;
        this.argumentacion = argumentacion;
        this.destino = destino;
        this.contexto = contexto;
        this.intervencion = intervencion;
    }

    public int getMuestra_id() {
        return muestra_id;
    }

    public void setMuestra_id(int muestra_id) {
        this.muestra_id = muestra_id;
    }

    public int getTipos_muestras_id() {
        return tipos_muestras_id;
    }

    public void setTipos_muestras_id(int tipos_muestras_id) {
        this.tipos_muestras_id = tipos_muestras_id;
    }

    public String getTipos_muestras_nombre() {
        return tipos_muestras_nombre;
    }

    public void setTipos_muestras_nombre(String tipos_muestras_nombre) {
        this.tipos_muestras_nombre = tipos_muestras_nombre;
    }

    public int getTipos_materiales_id() {
        return tipos_materiales_id;
    }

    public void setTipos_materiales_id(int tipos_materiales_id) {
        this.tipos_materiales_id = tipos_materiales_id;
    }

    public String getTipos_materiales_nombre() {
        return tipos_materiales_nombre;
    }

    public void setTipos_materiales_nombre(String tipos_materiales_nombre) {
        this.tipos_materiales_nombre = tipos_materiales_nombre;
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

    public String getArgumentacion() {
        return argumentacion;
    }

    public void setArgumentacion(String argumentacion) {
        this.argumentacion = argumentacion;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getContexto() {
        return contexto;
    }

    public void setContexto(String contexto) {
        this.contexto = contexto;
    }

    public String getIntervencion() {
        return intervencion;
    }

    public void setIntervencion(String intervencion) {
        this.intervencion = intervencion;
    }
}
