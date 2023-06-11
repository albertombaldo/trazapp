package com.tfg.trazapp.controller;

import com.tfg.trazapp.model.dao.ConsumeDAO;
import com.tfg.trazapp.model.dao.EnvioDAO;
import com.tfg.trazapp.model.dto.ConsumeDTO;
import com.tfg.trazapp.model.dto.EnvioDTO;
import com.tfg.trazapp.model.vo.ProductoFinal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class TrazabilidadController implements Initializable {
    @FXML
    private Button btnBuscar;
    @FXML
    private TableColumn colCantidadEnvio;
    @FXML
    private TableColumn colCantidadProducto;
    @FXML
    private TableColumn colCliente;
    @FXML
    private TableColumn colLotePF;
    @FXML
    private TableColumn colLoteProd;
    @FXML
    private TableColumn colNombreProdFinal;
    @FXML
    private TableColumn colNombreProd;
    @FXML
    private TableView<ConsumeDTO> listaConsumos;
    @FXML
    private TableView<EnvioDTO> listaEnvios;
    @FXML
    private TextField tfLote;

    private ObservableList<EnvioDTO> envios = FXCollections.observableArrayList();
    private ObservableList<ConsumeDTO> consumos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(listaConsumos!=null){
            this.colNombreProd.setCellValueFactory(new PropertyValueFactory("nombre_producto"));
            this.colLoteProd.setCellValueFactory(new PropertyValueFactory("lote_producto"));
            this.colCantidadProducto.setCellValueFactory(new PropertyValueFactory("cantidad_producto"));


            this.colCliente.setCellValueFactory(new PropertyValueFactory("cliente"));
            this.colNombreProdFinal.setCellValueFactory(new PropertyValueFactory("producto"));
            this.colLotePF.setCellValueFactory(new PropertyValueFactory("lote"));
            this.colCantidadEnvio.setCellValueFactory(new PropertyValueFactory("cantidad"));
        }
    }

    public void generarTrazabilidad(ActionEvent actionEvent) {
        mostrarEnvios(new EnvioDAO().getEnviosPorLoteProducto(tfLote.getText()));
        mostrarConsumos(new ConsumeDAO().getConsumosPorLoteProd(tfLote.getText()));
    }

    public void mostrarEnvios(JSONArray jsonenvios){
        this.listaEnvios.getItems().clear();
        JSONObject productoFinal;
        JSONObject cliente;
        for(int i = 0; i<jsonenvios.length(); i++){
            Long id = Long.parseLong(jsonenvios.getJSONObject(i).get("id_envio").toString());
            String albaran = jsonenvios.getJSONObject(i).get("albaran").toString();
            float cantidad = jsonenvios.getJSONObject(i).getFloat("cantidad");
            String fecha_envio = jsonenvios.getJSONObject(i).get("fechaEnvio").toString();
            String lote = jsonenvios.getJSONObject(i).get("lote").toString();
            productoFinal = jsonenvios.getJSONObject(i).getJSONObject("producto");
            cliente = jsonenvios.getJSONObject(i).getJSONObject("cliente");
            envios.add(new EnvioDTO(id, productoFinal.getString("nombre"), cliente.getString("nombre"), Date.valueOf(fecha_envio), cantidad, lote, albaran));
        }
        this.listaEnvios.setItems(envios);
    }

    public void mostrarConsumos(JSONArray jsonconsumos){
        this.listaConsumos.getItems().clear();
        JSONObject suministro;
        for(int i = 0; i<jsonconsumos.length(); i++){
            suministro = jsonconsumos.getJSONObject(i).getJSONObject("suministro");
            float cantidad = jsonconsumos.getJSONObject(i).getFloat("cantidad");
            consumos.add(new ConsumeDTO(suministro.getString("lote_producto"), suministro.getJSONObject("producto").getString("nombre"), cantidad));
        }

        listaConsumos.setItems(consumos);
    }

}
