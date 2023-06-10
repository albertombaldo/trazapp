package com.tfg.trazapp.model.dao;

import com.tfg.trazapp.model.vo.Cliente;
import com.tfg.trazapp.model.vo.Proveedor;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class ClienteDAO {
    public JSONArray getAllClientes(){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/cliente/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();
            if(responseCode != 200) {
                throw new RuntimeException("Ocurrió un error " + responseCode);
            }else {
                StringBuilder info = new StringBuilder();
                Scanner sc = new Scanner(url.openStream());
                while(sc.hasNext()) {
                    info.append(sc.nextLine());
                }
                sc.close();
                jarray = new JSONArray(info.toString());
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
    public JSONArray getClientePorNombre(String nombre){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/cliente/" + nombre);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();
            if(responseCode != 200) {
                throw new RuntimeException("Ocurrió un error " + responseCode);
            }else {
                StringBuilder info = new StringBuilder();
                Scanner sc = new Scanner(url.openStream());
                while(sc.hasNext()) {
                    info.append(sc.nextLine());
                }
                sc.close();
                jarray = new JSONArray(info.toString());
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
    public void anadirCliente(Cliente c) {
        try {
            URL url = new URL("http://localhost:8080/trazapp/cliente");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            if(!comprobarClienteExiste(c)){
                c.setId(0l);
                String json = new JSONObject(c).toString();
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Content-Length", Integer.toString(json.length()));
                conn.connect();
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Content-Length", Integer.toString(json.length()));
                conn.connect();
                try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(dos, "UTF-8"));
                    writer.write(json);
                    writer.close();
                }
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))){
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            } else {
                mostrarAlertError(new ActionEvent(), "Ya existe un cliente con el NIF introducido");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    public void modificarCliente(Cliente c) {
        try {
            URL url = new URL("http://localhost:8080/trazapp/cliente");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            String json = "{\"id_cliente\":"+ c.getId() +",\"nombre\":\""+ c.getNombre() +"\",\"nif\":\""+ c.getNif() +"\",\"telefono\":\""+ c.getTelefono() +"\",\"direccion\":\""+ c.getDireccion() +"\"}";
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Content-Length", Integer.toString(json.length()));
            conn.connect();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Content-Length", Integer.toString(json.length()));
            conn.connect();
            try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(dos, "UTF-8"));
                writer.write(json);
                writer.close();
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))){
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
    public void deleteCliente(Cliente c) {
        try {
            URL url = new URL("http://localhost:8080/trazapp/cliente?id=" + c.getId());
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
            mostrarAlertError(new ActionEvent(), "Acción no válida\nEl cliente posee albaranes asociados o no existe");
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //Como el NIF es único por cada empresa, es el campo que empleamos en la comparativa
    public boolean comprobarClienteExiste(Cliente c){
        boolean existe = false;
        JSONArray clientes = getAllClientes();
        for(int i = 0; i<clientes.length(); i++){
            if(c.getNif().equals(clientes.getJSONObject(i).get("nif").toString()))
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
