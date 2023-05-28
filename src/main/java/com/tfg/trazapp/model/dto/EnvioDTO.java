package com.tfg.trazapp.model.dto;

import java.sql.Date;

public class EnvioDTO {

    private Long id_envio;
    private String producto;
    private String cliente;
    private Date fechaEnvio;
    private Float cantidad;
    private String lote;
    private String albaran;

    public EnvioDTO(Long id_envio, String producto, String cliente, Date fechaEnvio, Float cantidad, String lote, String albaran) {
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

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
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
        return "EnvioDTO{" +
                "id_envio=" + id_envio +
                ", producto='" + producto + '\'' +
                ", cliente='" + cliente + '\'' +
                ", fechaEnvio=" + fechaEnvio +
                ", cantidad=" + cantidad +
                ", lote='" + lote + '\'' +
                ", albaran='" + albaran + '\'' +
                '}';
    }
}
