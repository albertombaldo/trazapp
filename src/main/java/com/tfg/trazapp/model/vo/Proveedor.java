package com.tfg.trazapp.model.vo;

public class Proveedor {

    private Long id_proveedor;
    private String nombre;
    private String nif;
    private String telefono;
    private String direccion;

    public Proveedor(Long id_proveedor, String nombre, String nif, String telefono, String direccion) {
        this.id_proveedor = id_proveedor;
        this.nombre = nombre;
        this.nif = nif;
        this.telefono = telefono;
        this.direccion = direccion;
    }


    @Override
    public String toString() {
        return "Proveedor{" +
                "id_proveedor=" + id_proveedor +
                ", nombre='" + nombre + '\'' +
                ", nif='" + nif + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }

    public Long getId() {
        return id_proveedor;
    }

    public void setId(Long id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
