package com.tfg.trazapp.model.dao;

import com.tfg.trazapp.model.vo.Suministro;
import com.tfg.trazapp.model.vo.Usuario;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.*;
import java.util.Scanner;

public class SuministroDAO {
    public JSONArray getAllSuministros(){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/suministro/");
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
                System.out.println(jobjeto.get("id_suministro"));
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
    public JSONArray getSuministroPorAlbaran(String albaran){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/suministro/albaran?num=" + albaran);
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
    public JSONArray getUltinmosSuministrosPorFechaAsc(Long id_producto){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/suministro/ultimos_suministros?id_producto="+id_producto);
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
    public void anadirSumistro(Suministro s) {
        try {
            URL url = new URL("http://localhost:8080/trazapp/suministro");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
                s.setId_suministro(0l);
                //No admite acentos, por eso se usa el StringUtils.stripAccents
                String json = StringUtils.stripAccents(new JSONObject(s).toString().replaceAll("\"id\":", "\"id_proveedor\":"));
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
            //conn.setUseCaches(false);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    public void modificarSuministro(Suministro s) {
        try {
            URL url = new URL("http://localhost:8080/trazapp/suministro/modificar_stock");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            String json = StringUtils.stripAccents(new JSONObject()
                    .put("id_suministro", s.getId_suministro())
                    .put("fecha_caducidad",  s.getFecha_caducidad())
                    .put("fecha_recepcion", s.getFecha_recepcion())
                    .put("proveedor", new JSONObject(s.getProveedor()))
                    .put("cantidad_recepcionada", s.getCantidad_recepcionada())
                    .put("cantidad_stock", s.getCantidad_stock())
                    .put("lote_producto", s.getLote_producto())
                    .put("producto", new JSONObject(s.getProducto()))
                    .put("albaran", s.getAlbaran())
                    .toString().replaceAll("\"id\":", "\"id_proveedor\":"));
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
    public void deleteSuministro(Long id) {
        try {
            URL url = new URL("http://localhost:8080/trazapp/suministro?id=" + id);
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
            mostrarAlertError(new ActionEvent(), "Acción no válida\nEl producto ha sido utilizado ya");
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    private void mostrarAlertError(ActionEvent event, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
