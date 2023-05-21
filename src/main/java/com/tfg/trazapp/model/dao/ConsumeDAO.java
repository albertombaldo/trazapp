package com.tfg.trazapp.model.dao;

import com.tfg.trazapp.model.vo.Consume;
import com.tfg.trazapp.model.vo.Suministro;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConsumeDAO {

    public void anadirConsumo(Consume c) {
        try {
            URL url = new URL("http://localhost:8080/trazapp/consumo");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            c.setId_consumo(0l);
            //No admite acentos, por eso se usa el StringUtils.stripAccents
            String json = StringUtils.stripAccents(new JSONObject()
                    .put("id_consumo", c.getId_consumo())
                    .put("cantidad",  c.getCantidad())
                    .put("lote_produccion", new JSONObject(c.getProduccion()))
                    .put("id_suministro", new JSONObject(c.getSuministro()))
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
