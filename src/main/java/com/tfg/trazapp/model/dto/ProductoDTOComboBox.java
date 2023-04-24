package com.tfg.trazapp.model.dto;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class ProductoDTOComboBox {

    private DatePicker fecha_caducidad;
    private ComboBox producto;
    private String cantidad_recepcionada;
    private String lote_producto;

    public ProductoDTOComboBox(DatePicker fecha_caducidad, ComboBox producto, String cantidad_recepcionada, String lote_producto) {
        this.fecha_caducidad = new DatePicker();
        this.producto = producto;
        this.cantidad_recepcionada = cantidad_recepcionada;
        this.lote_producto = lote_producto;
    }

    public DatePicker getFecha_caducidad() {
        return fecha_caducidad;
    }

    public void setFecha_caducidad(DatePicker fecha_caducidad) {
        this.fecha_caducidad = fecha_caducidad;
    }

    public ComboBox getProducto() {
        return producto;
    }

    public void setProducto(ComboBox producto) {
        this.producto = producto;
    }

    public String getCantidad_recepcionada() {
        return cantidad_recepcionada;
    }

    public void setCantidad_recepcionada(String cantidad_recepcionada) {
        this.cantidad_recepcionada = cantidad_recepcionada;
    }

    public String getLote_producto() {
        return lote_producto;
    }

    public void setLote_producto(String lote_producto) {
        this.lote_producto = lote_producto;
    }
}
