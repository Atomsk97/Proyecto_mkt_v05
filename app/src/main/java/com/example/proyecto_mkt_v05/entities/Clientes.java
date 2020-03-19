package com.example.proyecto_mkt_v05.entities;

public class Clientes {
    private String codigo, tipoDeNegocio, distrito, nombreDelMercado, giroDeNegocio, direccion,
            referencia, nombre, nombreAlternativo;

    public Clientes() {
    }

    public Clientes(String codigo, String nombreDelMercado, String direccion){
        this.codigo = codigo;
        this.nombreDelMercado = nombreDelMercado;
        this.direccion = direccion;
    }

    public Clientes(String codigo, String tipoDeNegocio, String distrito, String nombreDelMercado,
                    String giroDeNegocio, String direccion, String referencia, String nombre,
                    String nombreAlternativo) {
        this.codigo = codigo;
        this.tipoDeNegocio = tipoDeNegocio;
        this.distrito = distrito;
        this.nombreDelMercado = nombreDelMercado;
        this.giroDeNegocio = giroDeNegocio;
        this.direccion = direccion;
        this.referencia = referencia;
        this.nombre = nombre;
        this.nombreAlternativo = nombreAlternativo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipoDeNegocio() {
        return tipoDeNegocio;
    }

    public void setTipoDeNegocio(String tipoDeNegocio) {
        this.tipoDeNegocio = tipoDeNegocio;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getNombreDelMercado() {
        return nombreDelMercado;
    }

    public void setNombreDelMercado(String nombreDelMercado) {
        this.nombreDelMercado = nombreDelMercado;
    }

    public String getGiroDeNegocio() {
        return giroDeNegocio;
    }

    public void setGiroDeNegocio(String giroDeNegocio) {
        this.giroDeNegocio = giroDeNegocio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreAlternativo() {
        return nombreAlternativo;
    }

    public void setNombreAlternativo(String nombreAlternativo) {
        this.nombreAlternativo = nombreAlternativo;
    }
}
