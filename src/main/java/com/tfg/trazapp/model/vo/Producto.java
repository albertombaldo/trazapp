package com.tfg.trazapp.model.vo;

public class Producto {
    private Long id_producto;
    private String nombre;
    private String tipo;

    public Producto(Long id_producto, String nombre, String tipo) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public Long getId_producto() {
        return id_producto;
    }

    public void setId_producto(Long id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id_producto=" + id_producto +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
