package com.recolectarq.campo.recolectarq.Modelo;

import java.io.Serializable;
import java.math.BigDecimal;

public class Umtp implements Serializable {

    private int umtp_id;
    private int tipos_relieves_id;
    private int geoforma_id;
    private int vegetaciones_id;
    private int dedicaciones_entornos_id;
    private int proyectos_proyecto_id;
    private int numero;
    private BigDecimal largo;
    private BigDecimal ancho;
    private BigDecimal area;
    private int altura;
    private BigDecimal utmx;
    private BigDecimal utmy;
    private String latitud;
    private String longitud;
    private String municipio;
    private String departamento;
    private String vereda;
    private String sector;
    private String accesos;
    private String zona_incluyente;
    private String codigo_rotulo;
    private String yacimiento;
    private String sitio_potencial;
    private int usuarios_usuario_id;
    private int yacimiento_seccion;


    public Umtp() {
    }

    public Umtp(int umtp_id, int tipos_relieves_id, int geoforma_id, int vegetaciones_id, int dedicaciones_entornos_id,
                int proyectos_proyecto_id, int numero, BigDecimal largo, BigDecimal ancho, BigDecimal area, int altura,
                BigDecimal utmx, BigDecimal utmy, String latitud, String longitud, String municipio, String departamento,
                String vereda, String sector, String accesos, String zona_incluyente, String codigo_rotulo, String yacimiento,
                String sitio_potencial, int usuarios_usuario_id, int yacimiento_seccion) {
        this.umtp_id = umtp_id;
        this.tipos_relieves_id = tipos_relieves_id;
        this.geoforma_id = geoforma_id;
        this.vegetaciones_id = vegetaciones_id;
        this.dedicaciones_entornos_id = dedicaciones_entornos_id;
        this.proyectos_proyecto_id = proyectos_proyecto_id;
        this.numero = numero;
        this.largo = largo;
        this.ancho = ancho;
        this.area = area;
        this.altura = altura;
        this.utmx = utmx;
        this.utmy = utmy;
        this.latitud = latitud;
        this.longitud = longitud;
        this.municipio = municipio;
        this.departamento = departamento;
        this.vereda = vereda;
        this.sector = sector;
        this.accesos = accesos;
        this.zona_incluyente = zona_incluyente;
        this.codigo_rotulo = codigo_rotulo;
        this.yacimiento = yacimiento;
        this.sitio_potencial = sitio_potencial;
        this.usuarios_usuario_id = usuarios_usuario_id;
        this.yacimiento_seccion = yacimiento_seccion;
    }

    public int getUmtp_id() {
        return umtp_id;
    }

    public void setUmtp_id(int umtp_id) {
        this.umtp_id = umtp_id;
    }

    public int getTipos_relieves_id() {
        return tipos_relieves_id;
    }

    public void setTipos_relieves_id(int tipos_relieves_id) {
        this.tipos_relieves_id = tipos_relieves_id;
    }

    public int getGeoforma_id() {
        return geoforma_id;
    }

    public void setGeoforma_id(int geoforma_id) {
        this.geoforma_id = geoforma_id;
    }

    public int getVegetaciones_id() {
        return vegetaciones_id;
    }

    public void setVegetaciones_id(int vegetaciones_id) {
        this.vegetaciones_id = vegetaciones_id;
    }

    public int getDedicaciones_entornos_id() {
        return dedicaciones_entornos_id;
    }

    public void setDedicaciones_entornos_id(int dedicaciones_entornos_id) {
        this.dedicaciones_entornos_id = dedicaciones_entornos_id;
    }

    public int getProyectos_proyecto_id() {
        return proyectos_proyecto_id;
    }

    public void setProyectos_proyecto_id(int proyectos_proyecto_id) {
        this.proyectos_proyecto_id = proyectos_proyecto_id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public BigDecimal getLargo() {
        return largo;
    }

    public void setLargo(BigDecimal largo) {
        this.largo = largo;
    }

    public BigDecimal getAncho() {
        return ancho;
    }

    public void setAncho(BigDecimal ancho) {
        this.ancho = ancho;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public BigDecimal getUtmx() {
        return utmx;
    }

    public void setUtmx(BigDecimal utmx) {
        this.utmx = utmx;
    }

    public BigDecimal getUtmy() {
        return utmy;
    }

    public void setUtmy(BigDecimal utmy) {
        this.utmy = utmy;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getVereda() {
        return vereda;
    }

    public void setVereda(String vereda) {
        this.vereda = vereda;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getAccesos() {
        return accesos;
    }

    public void setAccesos(String accesos) {
        this.accesos = accesos;
    }

    public String getZona_incluyente() {
        return zona_incluyente;
    }

    public void setZona_incluyente(String zona_incluyente) {
        this.zona_incluyente = zona_incluyente;
    }

    public String getCodigo_rotulo() {
        return codigo_rotulo;
    }

    public void setCodigo_rotulo(String codigo_rotulo) {
        this.codigo_rotulo = codigo_rotulo;
    }

    public String getYacimiento() {
        return yacimiento;
    }

    public void setYacimiento(String yacimiento) {
        this.yacimiento = yacimiento;
    }

    public String getSitio_potencial() {
        return sitio_potencial;
    }

    public void setSitio_potencial(String sitio_potencial) {
        this.sitio_potencial = sitio_potencial;
    }

    public int getUsuarios_usuario_id() {
        return usuarios_usuario_id;
    }

    public void setUsuarios_usuario_id(int usuarios_usuario_id) {
        this.usuarios_usuario_id = usuarios_usuario_id;
    }

    public int getYacimiento_seccion() {
        return yacimiento_seccion;
    }

    public void setYacimiento_seccion(int yacimiento_seccion) {
        this.yacimiento_seccion = yacimiento_seccion;
    }
}
