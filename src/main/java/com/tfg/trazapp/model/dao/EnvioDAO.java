package com.tfg.trazapp.model.dao;

import com.tfg.trazapp.model.vo.Envio;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class EnvioDAO {
    public JSONArray getAllEnvios(){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/envio/");
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
    ///////////////////////////////////////////////////////////////
    public JSONArray getEnviosPorAlbaran(String albaran){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/envio/albaran/" + albaran);
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

    public void anadirEnvio(Envio e) {
        try {
            URL url = new URL("http://localhost:8080/trazapp/envio");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            String json = new JSONObject()
                    .put("id_envio", e.getId_envio())
                    .put("producto",  new JSONObject(e.getProducto()))
                    .put("cliente", new JSONObject(e.getCliente()))
                    .put("fechaEnvio", e.getFechaEnvio())
                    .put("cantidad", e.getCantidad())
                    .put("lote", e.getLote())
                    .put("albaran", e.getAlbaran())
                    .toString().replaceAll("\"id\":", "\"id_cliente\":");
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
        } catch (MalformedURLException es) {
            es.printStackTrace();
        } catch (IOException es) {
            es.printStackTrace();
        }
    }
}
