package com.tfg.trazapp.controller;

import com.tfg.trazapp.model.dao.EnvioDAO;
import com.tfg.trazapp.model.dto.EnvioDTO;
import com.tfg.trazapp.model.vo.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class EnviosController implements Initializable {

    //VENTANA DE PEDIDOS ENVIADOS
    @FXML
    private Button btnFiltrar;
    @FXML
    private TableColumn colCantidadProducto;
    @FXML
    private TableColumn colFechaEnvio;
    @FXML
    private TableColumn colIdAlbaran;
    @FXML
    private TableColumn colLoteProducto;
    @FXML
    private TableColumn colNombreCliente;
    @FXML
    private TableColumn colNombreProducto;
    @FXML
    private TableView<EnvioDTO> listaEnvios;
    @FXML
    private TextField tfAlbaranEnvio;

    private ObservableList<EnvioDTO> envios = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(listaEnvios != null){
            this.colIdAlbaran.setCellValueFactory(new PropertyValueFactory("albaran"));
            this.colFechaEnvio.setCellValueFactory(new PropertyValueFactory("fechaEnvio"));
            this.colNombreProducto.setCellValueFactory(new PropertyValueFactory("producto"));
            this.colLoteProducto.setCellValueFactory(new PropertyValueFactory("lote"));
            this.colCantidadProducto.setCellValueFactory(new PropertyValueFactory("cantidad"));
            this.colNombreCliente.setCellValueFactory(new PropertyValueFactory("cliente"));

            mostrarListaEnvios(new EnvioDAO().getAllEnvios());
        }
    }

    @FXML
    void clickAction(ActionEvent event) {
        if(event.getSource().equals(btnFiltrar)){
            JSONArray enviosAlbaran = new EnvioDAO().getEnviosPorAlbaran(tfAlbaranEnvio.getText());
            if(enviosAlbaran.length()>0){
                mostrarListaEnvios(enviosAlbaran);
            }else{
                this.listaEnvios.getItems().clear();
                this.listaEnvios.setPlaceholder(new Label("No se han encontrado resultados para su búsqueda"));
            }
        }
    }

    public void mostrarListaEnvios(JSONArray jsonenvios){
        this.listaEnvios.getItems().clear();
        for(int i = 0; i<jsonenvios.length(); i++){
            Long id = Long.parseLong(jsonenvios.getJSONObject(i).get("id_envio").toString());
            String producto = jsonenvios.getJSONObject(i).getJSONObject("producto").getString("nombre");
            String cliente = jsonenvios.getJSONObject(i).getJSONObject("cliente").getString("nombre");
            Date fecha_envio = Date.valueOf(jsonenvios.getJSONObject(i).get("fechaEnvio").toString());
            Float cantidad = Float.parseFloat(jsonenvios.getJSONObject(i).get("cantidad").toString());
            String lote = jsonenvios.getJSONObject(i).get("lote").toString();
            String albaran = jsonenvios.getJSONObject(i).get("albaran").toString();
            envios.add(new EnvioDTO(id, producto, cliente, fecha_envio, cantidad, lote, albaran));
        }
        this.listaEnvios.setItems(envios);
    }

    public void enterUnidades(ActionEvent actionEvent) {
    }

    public void alta(ActionEvent actionEvent) {
    }
}
