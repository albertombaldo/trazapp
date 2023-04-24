package com.tfg.trazapp.model.dto;

import com.tfg.trazapp.model.vo.Producto;
import com.tfg.trazapp.model.vo.Proveedor;

public class SuministroDTO {
    private Long id_suministro;
    private String fecha_recepcion;
    private String fecha_caducidad;
    private String proveedor;
    private String producto;
    private String albaran;
    private float cantidad_recepcionada;
    private float cantidad_stock;
    private String lote_producto;

    public SuministroDTO(Long id_suministro, String fecha_recepcion, String fecha_caducidad, String proveedor, String producto, String albaran, float cantidad_recepcionada, float cantidad_stock, String lote_producto) {
        this.id_suministro = id_suministro;
        this.fecha_recepcion = fecha_recepcion;
        this.fecha_caducidad = fecha_caducidad;
        this.proveedor = proveedor;
        this.producto = producto;
        this.albaran = albaran;
        this.cantidad_recepcionada = cantidad_recepcionada;
        this.cantidad_stock = cantidad_stock;
        this.lote_producto = lote_producto;
    }

    public Long getId_suministro() {
        return id_suministro;
    }

    public void setId_suministro(Long id_suministro) {
        this.id_suministro = id_suministro;
    }

    public String getFecha_recepcion() {
        return fecha_recepcion;
    }

    public void setFecha_recepcion(String fecha_recepcion) {
        this.fecha_recepcion = fecha_recepcion;
    }

    public String getFecha_caducidad() {
        return fecha_caducidad;
    }

    public void setFecha_caducidad(String fecha_caducidad) {
        this.fecha_caducidad = fecha_caducidad;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getAlbaran() {
        return albaran;
    }

    public void setAlbaran(String albaran) {
        this.albaran = albaran;
    }

    public float getCantidad_recepcionada() {
        return cantidad_recepcionada;
    }

    public void setCantidad_recepcionada(float cantidad) {
        this.cantidad_recepcionada = cantidad;
    }

    public float getCantidad_stock() {
        return cantidad_stock;
    }

    public void setCantidad_stock(float cantidad_stock) {
        this.cantidad_stock = cantidad_stock;
    }

    public String getLote_producto() {
        return lote_producto;
    }

    public void setLote_producto(String lote_producto) {
        this.lote_producto = lote_producto;
    }

}
