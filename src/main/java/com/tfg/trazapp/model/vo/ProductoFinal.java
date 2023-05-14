package com.tfg.trazapp.model.vo;

public class ProductoFinal {

    private Long id_producto_final;
    private String nombre;
    private Float peso_por_unidad;

    public ProductoFinal(Long id_producto_final, String nombre, Float peso_por_unidad) {
        this.id_producto_final = id_producto_final;
        this.nombre = nombre;
        this.peso_por_unidad = peso_por_unidad;
    }

    public Long getId_producto_final() {
        return id_producto_final;
    }

    public void setId_producto_final(Long id_producto_final) {
        this.id_producto_final = id_producto_final;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPeso_por_unidad() {
        return peso_por_unidad;
    }

    public void setPeso_por_unidad(Float peso_por_unidad) {
        this.peso_por_unidad = peso_por_unidad;
    }


}
