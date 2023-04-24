package com.tfg.trazapp.model.vo;

public class Cliente {

    private Long id_cliente;
    private String nombre;
    private String nif;
    private String telefono;
    private String direccion;

    public Cliente(Long id_cliente, String nombre, String nif, String telefono, String direccion) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.nif = nif;
        this.telefono = telefono;
        this.direccion = direccion;
    }


    @Override
    public String toString() {
        return "Cliente{" +
                "id_cliente=" + id_cliente +
                ", nombre='" + nombre + '\'' +
                ", nif='" + nif + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }

    public Long getId() {
        return id_cliente;
    }

    public void setId(Long id_cliente) {
        this.id_cliente = id_cliente;
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
