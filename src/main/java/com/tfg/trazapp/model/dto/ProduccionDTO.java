package com.tfg.trazapp.model.dto;

public class ProduccionDTO {
    private String lote_produccion;
    private String producto_final;
    private String fecha_produccion;
    private String fecha_caducidad;
    private Long unidades;
    private Long stock;

    public ProduccionDTO(String lote_produccion, String producto_final, String fecha_produccion, String fecha_caducidad, Long unidades, Long stock) {
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

    public String getProducto_final() {
        return producto_final;
    }

    public void setProducto_final(String producto_final) {
        this.producto_final = producto_final;
    }

    public String getFecha_produccion() {
        return fecha_produccion;
    }

    public void setFecha_produccion(String fecha_produccion) {
        this.fecha_produccion = fecha_produccion;
    }

    public String getFecha_caducidad() {
        return fecha_caducidad;
    }

    public void setFecha_caducidad(String fecha_caducidad) {
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
