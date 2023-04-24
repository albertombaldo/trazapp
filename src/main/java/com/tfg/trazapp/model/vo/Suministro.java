package com.tfg.trazapp.model.vo;

import java.sql.Date;

public class Suministro {

    private Long id_suministro;
    private Date fecha_recepcion;
    private Date fecha_caducidad;
    private Proveedor proveedor;
    private Producto producto;
    private String albaran;
    private float cantidad_recepcionada;
    private float cantidad_stock;
    private String lote_producto;

    public Suministro(Long id_suministro, Date fecha_recepcion, Date fecha_caducidad, Proveedor proveedor, Producto producto, String albaran, float cantidad_recepcionada, float cantidad_stock, String lote_producto) {
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

    public Date getFecha_recepcion() {
        return fecha_recepcion;
    }

    public void setFecha_recepcion(Date fecha_recepcion) {
        this.fecha_recepcion = fecha_recepcion;
    }

    public Date getFecha_caducidad() {
        return fecha_caducidad;
    }

    public void setFecha_caducidad(Date fecha_caducidad) {
        this.fecha_caducidad = fecha_caducidad;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
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

    @Override
    public String toString() {
        return "Suministro{" +
                "id_suministro=" + id_suministro +
                ", fecha_recepcion=" + fecha_recepcion +
                ", fecha_caducidad=" + fecha_caducidad +
                ", proveedor=" + proveedor +
                ", producto=" + producto +
                ", albaran='" + albaran + '\'' +
                ", cantidad_recepcionada=" + cantidad_recepcionada +
                ", cantidad_stock=" + cantidad_stock +
                ", lote_producto='" + lote_producto + '\'' +
                '}';
    }
}
