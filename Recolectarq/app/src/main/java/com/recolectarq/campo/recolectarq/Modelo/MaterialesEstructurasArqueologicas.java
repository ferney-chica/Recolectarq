package com.recolectarq.campo.recolectarq.Modelo;

public class MaterialesEstructurasArqueologicas {

    private int id;
    private int estructurasArqueologicasId;
    private  int tipoMaterialId;
    private String nombreTipoMaterial;

    public MaterialesEstructurasArqueologicas() {
    }

    public MaterialesEstructurasArqueologicas(int id, int estructurasArqueologicasId, int tipoMaterialId, String nombreTipoMaterial) {
        this.id = id;
        this.estructurasArqueologicasId = estructurasArqueologicasId;
        this.tipoMaterialId = tipoMaterialId;
        this.nombreTipoMaterial = nombreTipoMaterial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstructurasArqueologicasId() {
        return estructurasArqueologicasId;
    }

    public void setEstructurasArqueologicasId(int estructurasArqueologicasId) {
        this.estructurasArqueologicasId = estructurasArqueologicasId;
    }

    public int getTipoMaterialId() {
        return tipoMaterialId;
    }

    public void setTipoMaterialId(int tipoMaterialId) {
        this.tipoMaterialId = tipoMaterialId;
    }

    public String getNombreTipoMaterial() {
        return nombreTipoMaterial;
    }

    public void setNombreTipoMaterial(String nombreTipoMaterial) {
        this.nombreTipoMaterial = nombreTipoMaterial;
    }
}
