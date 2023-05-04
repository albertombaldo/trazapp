package com.tfg.trazapp.model.dao;

import com.tfg.trazapp.model.vo.Proveedor;
import javafx.collections.ObservableArray;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import org.json.JSONArray;
import org.json.JSONObject;

public class ProveedorDAO {
    public JSONArray getAllProveedores(){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/proveedor/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Comprobar que la peticion ha sido correcta (codigo 200)
            int responseCode = conn.getResponseCode();
            if(responseCode != 200) {
                throw new RuntimeException("Ocurrió un error " + responseCode);
            }else {
                //Abrir un Scanner que lea el flujo de datos de la URL e imprimirlo
                StringBuilder info = new StringBuilder();
                //Abrimos el flujo de datos de la URL dentro del Scanner
                Scanner sc = new Scanner(url.openStream());
                while(sc.hasNext()) {
                    info.append(sc.nextLine());
                }
                sc.close();
                //String consulta = "[" + info.toString() +"]";
                jarray = new JSONArray(info.toString());
                JSONObject jobjeto = jarray.getJSONObject(0);
                System.out.println(jobjeto.get("id_proveedor"));
                System.out.println(info);
            }
        } catch (MalformedURLException e) {
            System.err.println("URL incorrecta");
            e.printStackTrace();
        } catch (ProtocolException e) {
            System.err.println("Protocolo incorrecto");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jarray;
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    public JSONArray getProveedoresPorNombre(String nombre){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/proveedor/" + nombre);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            //Comprobar que la peticion ha sido correcta (codigo 200)
            int responseCode = conn.getResponseCode();
            if(responseCode != 200) {
                throw new RuntimeException("Ocurrió un error " + responseCode);
            }else {
                //Abrir un Scanner que lea el flujo de datos de la URL e imprimirlo

                StringBuilder info = new StringBuilder();

                //Abrimos el flujo de datos de la URL dentro del Scanner
                Scanner sc = new Scanner(url.openStream());
                while(sc.hasNext()) {
                    info.append(sc.nextLine());
                }
                sc.close();
                //String consulta = "[" + info.toString() +"]";
                jarray = new JSONArray(info.toString());
//                JSONObject jobjeto = jarray.getJSONObject(0);
//                System.out.println(jobjeto.get("id_proveedor"));
//                System.out.println(info);
            }
        } catch (MalformedURLException e) {
            System.err.println("URL incorrecta");
            e.printStackTrace();
        } catch (ProtocolException e) {
            System.err.println("Protocolo incorrecto");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jarray;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    public void anadirProveedor(Proveedor p) {
        try {
            URL url = new URL("http://localhost:8080/trazapp/proveedor");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            if(!comprobarProveedorExiste(p)){
                p.setId(0l);
                String json = new JSONObject(p).toString();
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Content-Length", Integer.toString(json.length()));
                conn.connect();
                try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
                    dos.writeBytes(json);
                }
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))){
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            } else {
                mostrarAlertError(new ActionEvent(), "Ya existe un proveedor con el NIF introducido");
            }
            //conn.setUseCaches(false);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    public void modificarProveedor(Proveedor p) {
        try {
            URL url = new URL("http://localhost:8080/trazapp/proveedor");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            String json = "{\"id_proveedor\":"+ p.getId() +",\"nombre\":\""+ p.getNombre() +"\",\"nif\":\""+ p.getNif() +"\",\"telefono\":\""+ p.getTelefono() +"\",\"direccion\":\""+ p.getDireccion() +"\"}";
            //String json = new JSONObject(p).toString();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Content-Length", Integer.toString(json.length()));
            conn.connect();
            try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
                dos.writeBytes(json);
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))){
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    public void deleteProveedor(Proveedor p) {
        try {
            URL url = new URL("http://localhost:8080/trazapp/proveedor?id=" + p.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.connect();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))){
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            mostrarAlertError(new ActionEvent(), "Acción no válida\nEl proveedor posee albaranes asociados o no existe");
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //Como el NIF es único por cada empresa, es el campo que empleamos en la comparativa
    public boolean comprobarProveedorExiste(Proveedor p){
        boolean existe = false;
        JSONArray provs = getAllProveedores();
        for(int i = 0; i<provs.length(); i++){
            if(p.getNif().equals(provs.getJSONObject(i).get("nif").toString()))
                existe = true;
        }
        return existe;
    }
    
    private void mostrarAlertError(ActionEvent event, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
