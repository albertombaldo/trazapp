package com.tfg.trazapp.model.dto;

public class ConsumeDTO{
        private String lote_producto;
        private String nombre_producto;
        private Float cantidad_producto;

    public ConsumeDTO(String lote_producto, String nombre_producto, Float cantidad_producto) {
        this.lote_producto = lote_producto;
        this.nombre_producto = nombre_producto;
        this.cantidad_producto = cantidad_producto;
    }

    public String getLote_producto() {
        return lote_producto;
    }

    public void setLote_producto(String lote_producto) {
        this.lote_producto = lote_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public Float getCantidad_producto() {
        return cantidad_producto;
    }

    public void setCantidad_producto(Float cantidad_producto) {
        this.cantidad_producto = cantidad_producto;
    }
}
