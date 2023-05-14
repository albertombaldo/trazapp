package com.tfg.trazapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProduccionesController implements Initializable {
    //PANTALLA STOCK PRODUCCIONES
    @FXML
    private Button btnEliminarRegistro;
    @FXML
    private Button btnFiltrar;
    @FXML
    private TableColumn colCantidad;
    @FXML
    private TableColumn colFechaCad;
    @FXML
    private TableColumn colFechaProduccion;
    @FXML
    private TableColumn colLote;
    @FXML
    private TableColumn colProducto;
    @FXML
    private TableColumn colStock;
    @FXML
    private TableView<?> listaProducciones;
    @FXML
    private TextField tfProducto;

    //PANTALLA NUEVA PRODUCCION
    @FXML
    private Button btnAlta;
    @FXML
    private ComboBox<?> cbProducto;
    @FXML
    private ComboBox<?> cbReceta;
    @FXML
    private ComboBox<?> cbCaja;
    @FXML
    private ComboBox<?> cbFilm;
    @FXML
    private TableColumn colCantidadProducto;
    @FXML
    private TableColumn colLoteProducto;
    @FXML
    private TableColumn colNombreProducto;
    @FXML
    private Label labelFechaProd;
    @FXML
    private Label labelFechaCad;
    @FXML
    private TableView<?> listaProductos;
    @FXML
    private TextField tfUnidades;
    @FXML
    private TextField tfDias;
    @FXML
    void clickAction(ActionEvent event) {

    }
    @FXML
    void eliminarRegistro(ActionEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(this.listaProducciones != null){
            this.colProducto.setCellValueFactory(new PropertyValueFactory("nombre"));
            this.colFechaProduccion.setCellValueFactory(new PropertyValueFactory("fecha_produccion"));
            this.colFechaCad.setCellValueFactory(new PropertyValueFactory("fecha_caducidad"));
            this.colCantidad.setCellValueFactory(new PropertyValueFactory("unidades"));
            this.colStock.setCellValueFactory(new PropertyValueFactory("stock"));
            this.colLote.setCellValueFactory(new PropertyValueFactory("lote_produccion"));
        }else if(listaProductos != null){
            this.colLoteProducto.setCellValueFactory(new PropertyValueFactory("lote_producto"));
            this.colNombreProducto.setCellValueFactory(new PropertyValueFactory("nombre_producto"));
            this.colCantidadProducto.setCellValueFactory(new PropertyValueFactory("cantidad_producto"));
            labelFechaProd.setText(LocalDate.now().toString());
        }
    }

    public void seleccionar(MouseEvent mouseEvent) {
    }

    public void alta(ActionEvent actionEvent) {
    }

    public void enterDias(ActionEvent actionEvent) {
        try{
            labelFechaCad.setText(LocalDate.now().plusDays(Integer.parseInt(tfDias.getText())).toString());
        }catch(NumberFormatException e){
            System.out.println("El número introducido no es un entero");
            labelFechaCad.setText("");
        }
    }
}
