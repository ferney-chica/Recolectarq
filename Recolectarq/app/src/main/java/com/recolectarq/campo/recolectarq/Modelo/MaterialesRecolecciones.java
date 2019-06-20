package com.recolectarq.campo.recolectarq.Modelo;

import java.io.Serializable;

public class MaterialesRecolecciones implements Serializable {

    private int material_recoleccion_id;
    private int flancos_geograficos_id;
    private String nombre_flanco_geografico;
    private int tipos_materiales_id;
    private String nombre_tipo_material;
    private int recolecciones_superficiales_recoleccion_superficial_id;
    private int cantidad;
    private String observacion;
    private String codigo_rotulo;
    private String elemento_diagnostico;
    private String observacion_elemento_diagnostico;

    public MaterialesRecolecciones() {
    }

    public MaterialesRecolecciones(int material_recoleccion_id, int flancos_geograficos_id, String nombre_flanco_geografico,
                                   int tipos_materiales_id, String nombre_tipo_material,
                                   int recolecciones_superficiales_recoleccion_superficial_id,
                                   int cantidad, String observacion, String codigo_rotulo,
                                   String elemento_diagnostico, String observacion_elemento_diagnostico) {
        this.material_recoleccion_id = material_recoleccion_id;
        this.flancos_geograficos_id = flancos_geograficos_id;
        this.nombre_flanco_geografico = nombre_flanco_geografico;
        this.tipos_materiales_id = tipos_materiales_id;
        this.nombre_tipo_material = nombre_tipo_material;
        this.recolecciones_superficiales_recoleccion_superficial_id = recolecciones_superficiales_recoleccion_superficial_id;
        this.cantidad = cantidad;
        this.observacion = observacion;
        this.codigo_rotulo = codigo_rotulo;
        this.elemento_diagnostico = elemento_diagnostico;
        this.observacion_elemento_diagnostico = observacion_elemento_diagnostico;
    }

    public int getMaterial_recoleccion_id() {
        return material_recoleccion_id;
    }

    public void setMaterial_recoleccion_id(int material_recoleccion_id) {
        this.material_recoleccion_id = material_recoleccion_id;
    }

    public int getFlancos_geograficos_id() {
        return flancos_geograficos_id;
    }

    public void setFlancos_geograficos_id(int flancos_geograficos_id) {
        this.flancos_geograficos_id = flancos_geograficos_id;
    }

    public String getNombre_flanco_geografico() {
        return nombre_flanco_geografico;
    }

    public void setNombre_flanco_geografico(String nombre_flanco_geografico) {
        this.nombre_flanco_geografico = nombre_flanco_geografico;
    }

    public int getTipos_materiales_id() {
        return tipos_materiales_id;
    }

    public void setTipos_materiales_id(int tipos_materiales_id) {
        this.tipos_materiales_id = tipos_materiales_id;
    }

    public String getNombre_tipo_material() {
        return nombre_tipo_material;
    }

    public void setNombre_tipo_material(String nombre_tipo_material) {
        this.nombre_tipo_material = nombre_tipo_material;
    }

    public int getRecolecciones_superficiales_recoleccion_superficial_id() {
        return recolecciones_superficiales_recoleccion_superficial_id;
    }

    public void setRecolecciones_superficiales_recoleccion_superficial_id(int recolecciones_superficiales_recoleccion_superficial_id) {
        this.recolecciones_superficiales_recoleccion_superficial_id = recolecciones_superficiales_recoleccion_superficial_id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getCodigo_rotulo() {
        return codigo_rotulo;
    }

    public void setCodigo_rotulo(String codigo_rotulo) {
        this.codigo_rotulo = codigo_rotulo;
    }

    public String getElemento_diagnostico() {
        return elemento_diagnostico;
    }

    public void setElemento_diagnostico(String elemento_diagnostico) {
        this.elemento_diagnostico = elemento_diagnostico;
    }

    public String getObservacion_elemento_diagnostico() {
        return observacion_elemento_diagnostico;
    }

    public void setObservacion_elemento_diagnostico(String observacion_elemento_diagnostico) {
        this.observacion_elemento_diagnostico = observacion_elemento_diagnostico;
    }
}
