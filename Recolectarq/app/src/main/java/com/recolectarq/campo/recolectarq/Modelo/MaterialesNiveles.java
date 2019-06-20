package com.recolectarq.campo.recolectarq.Modelo;

public class MaterialesNiveles {

    private int material_nivel_id;
    private int tipos_materiales_id;
    private String nombre_tipo_material;
    private int niveles_pozos_nivel_pozo_id;
    private int cantidad;
    private String observacion;
    private String elemento_diagnostico;
    private String observacion_elemento_diagnostico;

    public MaterialesNiveles() {
    }

    public MaterialesNiveles(int material_nivel_id, int tipos_materiales_id, String nombre_tipo_material,
                             int niveles_pozos_nivel_pozo_id, int cantidad, String observacion, String elemento_diagnostico,
                             String observacion_elemento_diagnostico) {
        this.material_nivel_id = material_nivel_id;
        this.tipos_materiales_id = tipos_materiales_id;
        this.nombre_tipo_material = nombre_tipo_material;
        this.niveles_pozos_nivel_pozo_id = niveles_pozos_nivel_pozo_id;
        this.cantidad = cantidad;
        this.observacion = observacion;
        this.elemento_diagnostico = elemento_diagnostico;
        this.observacion_elemento_diagnostico = observacion_elemento_diagnostico;
    }

    public int getMaterial_nivel_id() {
        return material_nivel_id;
    }

    public void setMaterial_nivel_id(int material_nivel_id) {
        this.material_nivel_id = material_nivel_id;
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

    public int getNiveles_pozos_nivel_pozo_id() {
        return niveles_pozos_nivel_pozo_id;
    }

    public void setNiveles_pozos_nivel_pozo_id(int niveles_pozos_nivel_pozo_id) {
        this.niveles_pozos_nivel_pozo_id = niveles_pozos_nivel_pozo_id;
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
