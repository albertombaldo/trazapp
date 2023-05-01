package com.tfg.trazapp.model.dto;

import com.tfg.trazapp.model.vo.Producto;

public class UtilizaDTO {
    private String producto;
    private float cantidad_mp;

    public UtilizaDTO(String producto, float cantidad_mp) {
        this.producto = producto;
        this.cantidad_mp = cantidad_mp;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public float getCantidad_mp() {
        return cantidad_mp;
    }

    public void setCantidad_mp(float cantidad_mp) {
        this.cantidad_mp = cantidad_mp;
    }
}
