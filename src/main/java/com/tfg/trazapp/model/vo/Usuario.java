package com.tfg.trazapp.model.vo;

import java.sql.*;

public class Usuario {

    Long id_usuario;
    String nombre;
    String rol;

    public Usuario(Long id_usuario, String nombre, String rol){
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.rol = rol;
    }

    public Usuario comprobarUsuario(String nombre, String pass){
        Connection conn = null;
        Usuario user = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/trazapp", "root", "");
            System.out.println("Se ha conectado correctamente a la base de datos");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + e.getErrorCode());
        }
        if(conn == null){
            System.out.println("No se ha podido conectar a la base de datos");
        }else{
            try {
                String sentencia = "select * from usuarios where nombre = '" + nombre + "' and pass = MD5('" + pass + "');";
                PreparedStatement ps = conn.prepareStatement(sentencia);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    user = new Usuario(rs.getLong(1), rs.getString(2), rs.getString(4));
                }
                conn.close();
                System.out.println("Conexión cerrada");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public boolean cambiarDatos(String nombreNuevo, String passNuevo){
        boolean cambiados = false;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/trazapp", "root", "");
            System.out.println("Se ha conectado correctamente a la base de datos");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + e.getErrorCode());
        }
        if(conn == null){
            System.out.println("No se ha podido conectar a la base de datos");
        }else{
            try {
                String sentencia = "update usuarios set nombre = '" + nombreNuevo + "', pass = MD5('" + passNuevo + "') where id_usuario = '" + getId_usuario() + "';";
                PreparedStatement ps = conn.prepareStatement(sentencia);
                int resultado = ps.executeUpdate();
                if(resultado != -1){
                    cambiados = true;
                }
                conn.close();
                System.out.println("Conexión cerrada");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cambiados;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Long getId_usuario() {return id_usuario;}

    public void setId_usuario(Long id_usuario) {this.id_usuario = id_usuario;}
}
