package com.tfg.trazapp.model.vo;

public class Consume {
    private Long id_consumo;
    private Produccion produccion;
    private Suministro suministro;
    private float cantidad;

    public Consume(Long id_consumo, Produccion produccion, Suministro suministro, float cantidad) {
        this.id_consumo = id_consumo;
        this.produccion = produccion;
        this.suministro = suministro;
        this.cantidad = cantidad;
    }

    public Long getId_consumo() {
        return id_consumo;
    }

    public void setId_consumo(Long id_consumo) {
        this.id_consumo = id_consumo;
    }

    public Produccion getProduccion() {
        return produccion;
    }

    public void setProduccion(Produccion produccion) {
        this.produccion = produccion;
    }

    public Suministro getSuministro() {
        return suministro;
    }

    public void setSuministro(Suministro suministro) {
        this.suministro = suministro;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }
}
