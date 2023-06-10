package com.tfg.trazapp.model.dao;

import com.tfg.trazapp.model.vo.Utiliza;
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
import com.tfg.trazapp.model.vo.Receta;


public class RecetaDAO {

    public JSONArray getAllRecetas(){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/receta/");
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
    public JSONArray getReceta(Long id){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/receta?id="+id);
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
    public JSONArray getRecetaPorNombre(String nombre){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/receta/"+nombre);
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
    public void anadirReceta(Receta r) {
        try {
            URL url = new URL("http://localhost:8080/trazapp/receta");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            if(!comprobarRecetaExiste(r)){
                r.setId_receta(0l);
                String json = new JSONObject(r).toString();
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
                mostrarAlertError(new ActionEvent(), "Ya existe una receta con el nombre introducido");
            }
            //conn.setUseCaches(false);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    public JSONArray getProductos(Long id_receta){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/producto/receta?id="+id_receta);
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

    ////////////////////////////////////////////////////////////////////////////
    public JSONArray getUsos(Long id_receta){
        JSONArray jarray = null;
        try {
            URL url = new URL("http://localhost:8080/trazapp/uso/receta?id="+id_receta);
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
    ////////////////////////////////////////////////////////////////////////////
    public void anadirUso(Utiliza u){
        try {
            URL url = new URL("http://localhost:8080/trazapp/uso");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            u.setId_uso(0l);
            String json = new JSONObject()
                    .put("id_uso", u.getId_uso())
                    .put("cantidad_mp",  u.getCantidad_mp())
                    .put("id_producto", new JSONObject(u.getProducto()))
                    .put("id_receta", new JSONObject(u.getReceta()))
                    .toString();
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
    public boolean comprobarRecetaExiste(Receta p){
        boolean existe = false;
        JSONArray recs = getAllRecetas();
        for(int i = 0; i<recs.length(); i++){
            if(p.getNombre().equals(recs.getJSONObject(i).get("nombre").toString()))
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
