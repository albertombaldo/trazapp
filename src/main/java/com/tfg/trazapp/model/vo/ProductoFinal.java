package com.tfg.trazapp.model.vo;

public class ProductoFinal {

    private Long id_producto_final;
    private String nombre;
    private Float peso_por_unidad;
    private Long unidades_por_paquete;
    private Long paquetes_por_caja;

    public ProductoFinal(Long id_producto_final, String nombre, Float peso_por_unidad, Long unidades_por_paquete, Long paquetes_por_caja) {
        this.id_producto_final = id_producto_final;
        this.nombre = nombre;
        this.peso_por_unidad = peso_por_unidad;
        this.unidades_por_paquete = unidades_por_paquete;
        this.paquetes_por_caja = paquetes_por_caja;
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

    public Long getUnidades_por_paquete() {
        return unidades_por_paquete;
    }

    public void setUnidades_por_paquete(Long unidades_por_paquete) {
        this.unidades_por_paquete = unidades_por_paquete;
    }

    public Long getPaquetes_por_caja() {
        return paquetes_por_caja;
    }

    public void setPaquetes_por_caja(Long paquetes_por_caja) {
        this.paquetes_por_caja = paquetes_por_caja;
    }

    @Override
    public String toString() {
        return "ProductoFinal{" +
                "id_producto_final=" + id_producto_final +
                ", nombre='" + nombre + '\'' +
                ", peso_por_unidad=" + peso_por_unidad +
                ", unidades_por_paquete=" + unidades_por_paquete +
                ", paquetes_por_caja=" + paquetes_por_caja +
                '}';
    }
}
