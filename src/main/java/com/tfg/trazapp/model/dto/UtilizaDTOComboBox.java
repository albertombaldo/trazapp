package com.tfg.trazapp.model.dto;

import javafx.scene.control.ComboBox;

public class UtilizaDTOComboBox {
    private ComboBox producto;
    private String cantidad_mp;

    public UtilizaDTOComboBox(ComboBox producto, String cantidad_mp) {
        this.producto = producto;
        this.cantidad_mp = cantidad_mp;
    }

    public ComboBox getProducto() {
        return producto;
    }

    public void setProducto(ComboBox producto) {
        this.producto = producto;
    }

    public String getCantidad_mp() {
        return cantidad_mp;
    }

    public void setCantidad_mp(String cantidad_mp) {
        this.cantidad_mp = cantidad_mp;
    }
}
