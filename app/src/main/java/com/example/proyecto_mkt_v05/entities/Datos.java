package com.example.proyecto_mkt_v05.entities;

public class Datos {
    private String cod_fecha, tipo_empresa, tipo_producto, marca_final, producto_final, cantidad_final, monto_final;

    public Datos() {
    }

    public Datos(String cod_fecha, String tipo_empresa, String tipo_producto, String marca_final,
                 String producto_final, String cantidad_final, String monto_final) {
        this.cod_fecha = cod_fecha;
        this.tipo_empresa = tipo_empresa;
        this.tipo_producto = tipo_producto;
        this.marca_final = marca_final;
        this.producto_final = producto_final;
        this.cantidad_final = cantidad_final;
        this.monto_final = monto_final;
    }

    public String getCod_fecha() {
        return cod_fecha;
    }

    public void setCod_fecha(String cod_fecha) {
        this.cod_fecha = cod_fecha;
    }

    public String getTipo_empresa() {
        return tipo_empresa;
    }

    public void setTipo_empresa(String tipo_empresa) {
        this.tipo_empresa = tipo_empresa;
    }

    public String getTipo_producto() {
        return tipo_producto;
    }

    public void setTipo_producto(String tipo_producto) {
        this.tipo_producto = tipo_producto;
    }

    public String getMarca_final() {
        return marca_final;
    }

    public void setMarca_final(String marca_final) {
        this.marca_final = marca_final;
    }

    public String getProducto_final() {
        return producto_final;
    }

    public void setProducto_final(String producto_final) {
        this.producto_final = producto_final;
    }

    public String getCantidad_final() {
        return cantidad_final;
    }

    public void setCantidad_final(String cantidad_final) {
        this.cantidad_final = cantidad_final;
    }

    public String getMonto_final() {
        return monto_final;
    }

    public void setMonto_final(String monto_final) {
        this.monto_final = monto_final;
    }
}
