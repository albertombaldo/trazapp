package com.tfg.trazapp.model.dao;

import com.tfg.trazapp.model.vo.Produccion;
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

    public void anadirProduccion(Produccion p) {
        try {
            URL url = new URL("http://localhost:8080/trazapp/produccion");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            //No admite acentos, por eso se usa el StringUtils.stripAccents
            //Cuando pasa a JSON el proveedor cambia id_proveedor por id sin motivo, por lo que se sustituye usando .replaceAll("\"id\":", "\"id_proveedor\":")
            String json = "{\"lote_produccion\":"+ p.getLote_produccion() +",\"fecha_caducidad\":\""+ p.getFecha_caducidad() +"\",\"fecha_produccion\":\""+ p.getFecha_produccion() +
                    "\",\"producto_final\":"+ new JSONObject(p.getProducto_final()) +",\"stock\":\""+ p.getStock() +"\",\" unidades\":\""+p.getUnidades()+"\" }";
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
            //conn.setUseCaches(false);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
