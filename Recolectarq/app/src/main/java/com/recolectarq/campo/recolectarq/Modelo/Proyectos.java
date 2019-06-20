package com.recolectarq.campo.recolectarq.Modelo;

import java.io.Serializable;
import java.util.Date;

public class Proyectos implements Serializable {

    private int proyecto_id;
    private int usuario_creador;
    private int usuario_id;
    private String nombre_perfil;
    private int perfil_id;
    private String nombre_tipo_proyecto;
    private int tipo_proyecto_id;
    private String nombre_fases_proyectos;
    private int fase_proyecto_id;
    private String nombre;
    private String ubicacion;
    private Date fecha_inicio;
    private Date fecha_fin;
    private String Referencias_administrativas;
    private String aval_cientifico;
    private String codigo_identificacion;

    public Proyectos() {
    }

    public Proyectos(int proyecto_id, int usuario_creador, int usuario_id, String nombre_perfil, int perfil_id, String nombre_tipo_proyecto,
                     int tipo_proyecto_id, String nombre_fases_proyectos, int fase_proyecto_id, String nombre, String ubicacion,
                     Date fecha_inicio, Date fecha_fin, String referencias_administrativas, String aval_cientifico, String codigo_identificacion) {
        this.proyecto_id = proyecto_id;
        this.usuario_creador = usuario_creador;
        this.usuario_id = usuario_id;
        this.nombre_perfil = nombre_perfil;
        this.perfil_id = perfil_id;
        this.nombre_tipo_proyecto = nombre_tipo_proyecto;
        this.tipo_proyecto_id = tipo_proyecto_id;
        this.nombre_fases_proyectos = nombre_fases_proyectos;
        this.fase_proyecto_id = fase_proyecto_id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        Referencias_administrativas = referencias_administrativas;
        this.aval_cientifico = aval_cientifico;
        this.codigo_identificacion = codigo_identificacion;
    }

    public int getProyecto_id() {
        return proyecto_id;
    }

    public void setProyecto_id(int proyecto_id) {
        this.proyecto_id = proyecto_id;
    }

    public int getUsuario_creador() {
        return usuario_creador;
    }

    public void setUsuario_creador(int usuario_creador) {
        this.usuario_creador = usuario_creador;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getNombre_perfil() {
        return nombre_perfil;
    }

    public void setNombre_perfil(String nombre_perfil) {
        this.nombre_perfil = nombre_perfil;
    }

    public int getPerfil_id() {
        return perfil_id;
    }

    public void setPerfil_id(int perfil_id) {
        this.perfil_id = perfil_id;
    }

    public String getNombre_tipo_proyecto() {
        return nombre_tipo_proyecto;
    }

    public void setNombre_tipo_proyecto(String nombre_tipo_proyecto) {
        this.nombre_tipo_proyecto = nombre_tipo_proyecto;
    }

    public int getTipo_proyecto_id() {
        return tipo_proyecto_id;
    }

    public void setTipo_proyecto_id(int tipo_proyecto_id) {
        this.tipo_proyecto_id = tipo_proyecto_id;
    }

    public String getNombre_fases_proyectos() {
        return nombre_fases_proyectos;
    }

    public void setNombre_fases_proyectos(String nombre_fases_proyectos) {
        this.nombre_fases_proyectos = nombre_fases_proyectos;
    }

    public int getFase_proyecto_id() {
        return fase_proyecto_id;
    }

    public void setFase_proyecto_id(int fase_proyecto_id) {
        this.fase_proyecto_id = fase_proyecto_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getReferencias_administrativas() {
        return Referencias_administrativas;
    }

    public void setReferencias_administrativas(String referencias_administrativas) {
        Referencias_administrativas = referencias_administrativas;
    }

    public String getAval_cientifico() {
        return aval_cientifico;
    }

    public void setAval_cientifico(String aval_cientifico) {
        this.aval_cientifico = aval_cientifico;
    }

    public String getCodigo_identificacion() {
        return codigo_identificacion;
    }

    public void setCodigo_identificacion(String codigo_identificacion) {
        this.codigo_identificacion = codigo_identificacion;
    }
}
