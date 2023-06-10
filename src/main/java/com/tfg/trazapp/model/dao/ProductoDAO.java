package com.tfg.trazapp.model.dao;

import com.tfg.trazapp.model.vo.Producto;
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

public class ProductoDAO {
    public JSONArray getAllProductos(){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/producto/");
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
    public JSONArray getProductosPorNombre(String nombre){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/producto/" + nombre);
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
    public JSONArray getProductoPorNombre(String nombre){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/producto/nombre/" + nombre);
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
                String consulta = "[" + info.toString() +"]";
                jarray = new JSONArray(consulta);
                //JSONObject jobjeto = jarray.getJSONObject(0);
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
    public JSONArray getProductosPorTipo(String tipo){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/producto/tipo/" + tipo);
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
                //JSONObject jobjeto = jarray.getJSONObject(0);
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
    public void modificarProducto(Producto p) {
        try {
            URL url = new URL("http://localhost:8080/trazapp/producto");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            String json = "{\"id_producto\":"+ p.getId_producto() +",\"nombre\":\""+ p.getNombre() +"\",\"tipo\":\""+ p.getTipo() +"\"}";
            //String json = new JSONObject(p).toString();
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

    ////////////////////////////////////////////////////////////////////////////////////////////
    public void anadirProducto(Producto p) {
        try {
            URL url = new URL("http://localhost:8080/trazapp/producto");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            if(!comprobarPrductoExiste(p)){
                p.setId_producto(0l);
                String json = new JSONObject(p).toString();
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
                mostrarAlertError(new ActionEvent(), "Ya existe un producto con el mismo nombre");
            }
            //conn.setUseCaches(false);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    ////////////////////////////////////////////////////////////////////////////
    public void deleteProducto(Producto p) {
        try {
            URL url = new URL("http://localhost:8080/trazapp/producto?id=" + p.getId_producto());
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
            mostrarAlertError(new ActionEvent(), "Acción no válida\nEl producto posee entradas asociadas o no existe");
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    public boolean comprobarPrductoExiste(Producto p){
        boolean existe = false;
        JSONArray prods = getAllProductos();
        for(int i = 0; i<prods.length(); i++){
            if(p.getNombre().equals(prods.getJSONObject(i).get("nombre").toString()))
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
