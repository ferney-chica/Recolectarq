package com.recolectarq.campo.recolectarq.Modelo;

import java.io.Serializable;

public class Usuarios implements Serializable{

    private String usuario_id;
    private String nombre;
    private String apellido;
    private String contrasena;

    public Usuarios() {
    }

    public Usuarios(String usuario_id, String nombre, String apellido, String contrasena) {
        this.usuario_id = usuario_id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasena = contrasena;
    }



    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
