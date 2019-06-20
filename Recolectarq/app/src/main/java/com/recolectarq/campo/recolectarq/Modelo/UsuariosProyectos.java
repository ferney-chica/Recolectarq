package com.recolectarq.campo.recolectarq.Modelo;

import java.io.Serializable;

public class UsuariosProyectos implements Serializable {

    private int proyecto_id;
    private int usuario_id;
    private int perfil_id;
    private String proyecto_nombre;
    private String usuario_nombre;
    private String usuario_apellido;
    private String perfil_descripcion;

    public String getProyecto_nombre() {
        return proyecto_nombre;
    }

    public UsuariosProyectos(int proyecto_id, int usuario_id, int perfil_id, String proyecto_nombre, String usuario_nombre, String usuario_apellido, String perfil_descripcion) {
        this.proyecto_id = proyecto_id;
        this.usuario_id = usuario_id;
        this.perfil_id = perfil_id;
        this.proyecto_nombre = proyecto_nombre;
        this.usuario_nombre = usuario_nombre;
        this.usuario_apellido = usuario_apellido;
        this.perfil_descripcion = perfil_descripcion;
    }

    public int getProyecto_id() {
        return proyecto_id;
    }

    public void setProyecto_id(int proyecto_id) {
        this.proyecto_id = proyecto_id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public int getPerfil_id() {
        return perfil_id;
    }

    public void setPerfil_id(int perfil_id) {
        this.perfil_id = perfil_id;
    }

    public void setProyecto_nombre(String proyecto_nombre) {
        this.proyecto_nombre = proyecto_nombre;
    }

    public String getUsuario_nombre() {
        return usuario_nombre;
    }

    public void setUsuario_nombre(String usuario_nombre) {
        this.usuario_nombre = usuario_nombre;
    }

    public String getUsuario_apellido() {
        return usuario_apellido;
    }

    public void setUsuario_apellido(String usuario_apellido) {
        this.usuario_apellido = usuario_apellido;
    }

    public String getPerfil_descripcion() {
        return perfil_descripcion;
    }

    public void setPerfil_descripcion(String perfil_descripcion) {
        this.perfil_descripcion = perfil_descripcion;
    }
}
