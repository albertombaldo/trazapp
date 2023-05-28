package com.tfg.trazapp.model.dao;

import com.tfg.trazapp.model.vo.Produccion;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
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
import java.util.Scanner;

public class ProduccionDAO {

    public JSONArray getAllProducciones(){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/produccion/");
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

    ////////////////////////////////////////////////////////////////////////////////

    public JSONArray getProduccion(Long id){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/produccion?id="+id);
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
                String consulta = "[" + info.toString() +"]";
                jarray = new JSONArray(consulta);
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

    ////////////////////////////////////////////////////////////////////////////////
    public JSONArray getProduccionesPorProducto(Long id){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/produccion/id_producto/"+id);
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

    ////////////////////////////////////////////////////////////////////////////////
    public JSONArray getProduccionesPorProductoYFechaAsc(Long id){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/produccion/id_producto/"+id+"/porfechaasc");
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
    ////////////////////////////////////////////////////////////////////////////////

    public void anadirProduccion(Produccion p) {
        try {
            URL url = new URL("http://localhost:8080/trazapp/produccion");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            String json = new JSONObject()
                    .put("lote_produccion", p.getLote_produccion())
                    .put("fecha_caducidad",  p.getFecha_caducidad())
                    .put("fecha_produccion", p.getFecha_produccion())
                    .put("producto_final", new JSONObject(p.getProducto_final()))
                    .put("stock", p.getStock())
                    .put("unidades", p.getUnidades())
                    .toString();
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
    public void deleteProduccion(String lote) {
        try {
            URL url = new URL("http://localhost:8080/trazapp/produccion?id=" + lote);
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
            mostrarAlertError(new ActionEvent(), "El registro no puede eliminarse ya que ha sido enviado a cliente");
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
