package com.tfg.trazapp.model.vo;

import java.sql.Date;

public class Produccion {
    private String lote_produccion;
    private ProductoFinal producto_final;
    private Date fecha_produccion;
    private Date fecha_caducidad;
    private Long unidades;
    private Long stock;

    public Produccion(String lote_produccion, ProductoFinal producto_final, Date fecha_produccion, Date fecha_caducidad, Long unidades, Long stock) {
        this.lote_produccion = lote_produccion;
        this.producto_final = producto_final;
        this.fecha_produccion = fecha_produccion;
        this.fecha_caducidad = fecha_caducidad;
        this.unidades = unidades;
        this.stock = stock;
    }

    public String getLote_produccion() {
        return lote_produccion;
    }

    public void setLote_produccion(String lote_produccion) {
        this.lote_produccion = lote_produccion;
    }

    public ProductoFinal getProducto_final() {
        return producto_final;
    }

    public void setProducto_final(ProductoFinal producto_final) {
        this.producto_final = producto_final;
    }

    public Date getFecha_produccion() {
        return fecha_produccion;
    }

    public void setFecha_produccion(Date fecha_produccion) {
        this.fecha_produccion = fecha_produccion;
    }

    public Date getFecha_caducidad() {
        return fecha_caducidad;
    }

    public void setFecha_caducidad(Date fecha_caducidad) {
        this.fecha_caducidad = fecha_caducidad;
    }

    public Long getUnidades() {
        return unidades;
    }

    public void setUnidades(Long unidades) {
        this.unidades = unidades;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }
}
