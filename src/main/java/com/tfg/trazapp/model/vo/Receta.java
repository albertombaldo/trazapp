package com.tfg.trazapp.model.vo;

import java.util.List;

public class Receta {
    private Long id_receta;
    private String nombre;
    private List<Producto> materias_primas;

    public Receta(Long id_receta, String nombre, List<Producto> materias_primas) {
        this.id_receta = id_receta;
        this.nombre = nombre;
        this.materias_primas = materias_primas;
    }

    public List<Producto> getMaterias_primas() {
        return materias_primas;
    }

    public void setMaterias_primas(List<Producto> materias_primas) {
        this.materias_primas = materias_primas;
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
