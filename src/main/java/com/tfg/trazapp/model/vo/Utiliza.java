package com.tfg.trazapp.model.vo;

public class Utiliza {
    private Long id_uso;
    private Receta receta;
    private Producto producto;
    private float cantidad_mp;

    public Utiliza(Long id_uso, Receta receta, Producto producto, float cantidad_mp) {
        this.id_uso = id_uso;
        this.receta = receta;
        this.producto = producto;
        this.cantidad_mp = cantidad_mp;
    }

    public Long getId_uso() {
        return this.id_uso;
    }

    public void setId_uso(Long id_uso) {
        this.id_uso = id_uso;
    }

    public Receta getReceta() {
        return this.receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public float getCantidad_mp() {
        return this.cantidad_mp;
    }

    public void setCantidad_mp(float cantidad_mp) {
        this.cantidad_mp = cantidad_mp;
    }
}
