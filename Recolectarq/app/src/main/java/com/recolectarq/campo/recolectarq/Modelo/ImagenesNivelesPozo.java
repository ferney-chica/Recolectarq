package com.recolectarq.campo.recolectarq.Modelo;

public class ImagenesNivelesPozo {
    private int imagen_nivel_pozo_id;
    private int niveles_pozos_nivel_pozo_id;
    private String nombre;

    public ImagenesNivelesPozo() {
    }

    public ImagenesNivelesPozo(int imagen_nivel_pozo_id, int niveles_pozos_nivel_pozo_id, String nombre) {
        this.imagen_nivel_pozo_id = imagen_nivel_pozo_id;
        this.niveles_pozos_nivel_pozo_id = niveles_pozos_nivel_pozo_id;
        this.nombre = nombre;
    }

    public int getImagen_nivel_pozo_id() {
        return imagen_nivel_pozo_id;
    }

    public void setImagen_nivel_pozo_id(int imagen_nivel_pozo_id) {
        this.imagen_nivel_pozo_id = imagen_nivel_pozo_id;
    }

    public int getNiveles_pozos_nivel_pozo_id() {
        return niveles_pozos_nivel_pozo_id;
    }

    public void setNiveles_pozos_nivel_pozo_id(int niveles_pozos_nivel_pozo_id) {
        this.niveles_pozos_nivel_pozo_id = niveles_pozos_nivel_pozo_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
