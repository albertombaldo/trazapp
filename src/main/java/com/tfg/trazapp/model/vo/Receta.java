package com.tfg.trazapp.model.vo;

import java.util.List;

public class Receta {
    private Long id_receta;
    private String nombre;

    public Receta(Long id_receta, String nombre) {
        this.id_receta = id_receta;
        this.nombre = nombre;
    }

    public Long getId_receta() {
        return id_receta;
    }

    public void setId_receta(Long id_receta) {
        this.id_receta = id_receta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
