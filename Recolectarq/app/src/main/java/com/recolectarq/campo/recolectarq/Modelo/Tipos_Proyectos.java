package com.recolectarq.campo.recolectarq.Modelo;

public class Tipos_Proyectos {

    private int tipo_proyecto_id;
    private String nombre;

    public int getTipo_proyecto_id() {
        return tipo_proyecto_id;
    }

    public void setTipo_proyecto_id(int tipo_proyecto_id) {
        this.tipo_proyecto_id = tipo_proyecto_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tipos_Proyectos(int tipo_proyecto_id, String nombre) {
        this.tipo_proyecto_id = tipo_proyecto_id;
        this.nombre = nombre;
    }

}
