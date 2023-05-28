package com.tfg.trazapp.model.vo;

import java.sql.Date;

public class Envio {
    private Long id_envio;
    private ProductoFinal producto;
    private Cliente cliente;
    private Date fechaEnvio;
    private Float cantidad;
    private String lote;
    private String albaran;

    public Envio(Long id_envio, ProductoFinal producto, Cliente cliente, Date fechaEnvio, Float cantidad, String lote, String albaran) {
        this.id_envio = id_envio;
        this.producto = producto;
        this.cliente = cliente;
        this.fechaEnvio = fechaEnvio;
        this.cantidad = cantidad;
        this.lote = lote;
        this.albaran = albaran;
    }

    public Long getId_envio() {
        return id_envio;
    }

    public void setId_envio(Long id_envio) {
        this.id_envio = id_envio;
    }

    public ProductoFinal getProducto() {
        return producto;
    }

    public void setProducto(ProductoFinal producto) {
        this.producto = producto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getAlbaran() {
        return albaran;
    }

    public void setAlbaran(String albaran) {
        this.albaran = albaran;
    }

    @Override
    public String toString() {
        return "Envio{" +
                "id_envio=" + id_envio +
                ", producto=" + producto +
                ", cliente=" + cliente +
                ", fechaEnvio=" + fechaEnvio +
                ", cantidad=" + cantidad +
                ", lote='" + lote + '\'' +
                ", albaran='" + albaran + '\'' +
                '}';
    }
}
