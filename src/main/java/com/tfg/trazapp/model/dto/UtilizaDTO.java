package com.tfg.trazapp.model.dto;

import com.tfg.trazapp.model.vo.Producto;

public class UtilizaDTO {
    private Producto producto;
    private float cantidad_mp;

    public UtilizaDTO(Producto producto, float cantidad_mp) {
        this.producto = producto;
        this.cantidad_mp = cantidad_mp;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public float getCantidad_mp() {
        return cantidad_mp;
    }

    public void setCantidad_mp(float cantidad_mp) {
        this.cantidad_mp = cantidad_mp;
    }
}
