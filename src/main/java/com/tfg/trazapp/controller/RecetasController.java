package com.tfg.trazapp.controller;

import com.tfg.trazapp.model.dao.ClienteDAO;
import com.tfg.trazapp.model.dao.RecetaDAO;
import com.tfg.trazapp.model.dto.RecetaDTOSinLista;
import com.tfg.trazapp.model.dto.UtilizaDTO;
import com.tfg.trazapp.model.vo.Cliente;
import com.tfg.trazapp.model.vo.Receta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.json.JSONArray;

import java.net.URL;
import java.util.ResourceBundle;

public class RecetasController implements Initializable{

    //MENU GESTION
    @FXML
    private Button btnAnadir;
    @FXML
    private Button btnGuardarReceta;
    @FXML
    private TableColumn<?, ?> colCantidad;
    @FXML
    private TableColumn<?, ?> colIdReceta;
    @FXML
    private TableColumn<?, ?> colNombreReceta;
    @FXML
    private TableColumn<?, ?> colProducto;
    @FXML
    private TableView<?> listaEntradaProductos;
    @FXML
    private TableView<?> listaEntradaProductos1;
    @FXML
    private TextField tfNombreReceta;

    //MENU LISTADO
    @FXML
    private Button btnFiltrarListado;
    @FXML
    private TableColumn colCantidadProductoListado;
    @FXML
    private TableColumn colIdRecetaListado;
    @FXML
    private TableColumn colNombreProductoListado;
    @FXML
    private TableColumn colNombreRecetaListado;
    @FXML
    private Label labelNombreReceta;
    @FXML
    private TableView<UtilizaDTO> listaProductosListado;
    @FXML
    private TableView<RecetaDTOSinLista> listaRecetasListado;
    @FXML
    private TextField tfNombreRecetaListado;

    private ObservableList<RecetaDTOSinLista> recetas = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(this.listaRecetasListado != null){
            this.colIdRecetaListado.setCellValueFactory(new PropertyValueFactory("id_receta"));
            this.colNombreRecetaListado.setCellValueFactory(new PropertyValueFactory("nombre"));
            this.colNombreProductoListado.setCellValueFactory(new PropertyValueFactory("producto"));
            this.colCantidadProductoListado.setCellValueFactory(new PropertyValueFactory("telefono"));

            mostrarListaRecetas(new RecetaDAO().getAllRecetas());
        }
    }

    @FXML
    public void mostrarListaRecetas(JSONArray jsonrecetas){
        this.listaRecetasListado.getItems().clear();
        for(int i = 0; i<jsonrecetas.length(); i++){
            Long id = Long.parseLong(jsonrecetas.getJSONObject(i).get("id_receta").toString());
            String nombre = jsonrecetas.getJSONObject(i).get("nombre").toString();
            recetas.add(new RecetaDTOSinLista(id, nombre));
        }
        this.listaRecetasListado.setItems(recetas);
    }
    @FXML
    void anadirRegistro(ActionEvent event) {
    }

    @FXML
    void clickAction(ActionEvent event) {
    }

    @FXML
    void onEditChanged(ActionEvent event) {
    }

    public void seleccionar(MouseEvent mouseEvent) {
    }
}