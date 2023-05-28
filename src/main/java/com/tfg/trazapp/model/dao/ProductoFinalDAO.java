package com.tfg.trazapp.model.dao;

import com.tfg.trazapp.model.vo.Consume;
import com.tfg.trazapp.model.vo.ProductoFinal;
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
import java.util.Scanner;

public class ProductoFinalDAO {
    public JSONArray getAllProductos(){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/productofinal/");
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


    ///////////////////////////////////////////////////////////////////////////////////////
    public JSONArray getProductoFinalPorNombre(String nombre){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/productofinal/"+nombre);
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
    public void anadirProductoFinal(ProductoFinal pf) {
        try {
            URL url = new URL("http://localhost:8080/trazapp/productofinal");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            pf.setId_producto_final(0l);
            String json = StringUtils.stripAccents(new JSONObject()
                    .put("id_producto_final", pf.getId_producto_final())
                    .put("nombre", pf.getNombre())
                    .put("peso_por_unidad",  pf.getPeso_por_unidad())
                    .put("unidades_por_paquete",  pf.getUnidades_por_paquete())
                    .put("paquetes_por_caja",  pf.getPaquetes_por_caja())
                    .toString());
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
}
