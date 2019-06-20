package com.recolectarq.campo.recolectarq.Modelo;

public class MaterialesEstratosPerfiles {

    private int material_estrato_perfil_id;
    private int estratos_perfiles_estrato_perfil_id;
    private int tipos_materiales_id;
    private String tipos_materiales_nombre;
    private int cantidad;
    private String observacion;
    private String elemento_diagnostico;
    private String observacion_elemento_diagnostico;

    public MaterialesEstratosPerfiles() {
    }

    public MaterialesEstratosPerfiles(int material_estrato_perfil_id, int estratos_perfiles_estrato_perfil_id,
                                      int tipos_materiales_id, String tipos_materiales_nombre, int cantidad,
                                      String observacion, String elemento_diagnostico, String observacion_elemento_diagnostico) {
        this.material_estrato_perfil_id = material_estrato_perfil_id;
        this.estratos_perfiles_estrato_perfil_id = estratos_perfiles_estrato_perfil_id;
        this.tipos_materiales_id = tipos_materiales_id;
        this.tipos_materiales_nombre = tipos_materiales_nombre;
        this.cantidad = cantidad;
        this.observacion = observacion;
        this.elemento_diagnostico = elemento_diagnostico;
        this.observacion_elemento_diagnostico = observacion_elemento_diagnostico;
    }

    public int getMaterial_estrato_perfil_id() {
        return material_estrato_perfil_id;
    }

    public void setMaterial_estrato_perfil_id(int material_estrato_perfil_id) {
        this.material_estrato_perfil_id = material_estrato_perfil_id;
    }

    public int getEstratos_perfiles_estrato_perfil_id() {
        return estratos_perfiles_estrato_perfil_id;
    }

    public void setEstratos_perfiles_estrato_perfil_id(int estratos_perfiles_estrato_perfil_id) {
        this.estratos_perfiles_estrato_perfil_id = estratos_perfiles_estrato_perfil_id;
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
