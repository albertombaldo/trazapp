package com.tfg.trazapp.controller;

import com.tfg.trazapp.model.vo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {

    ScenesController sc;
    Usuario user;
    @FXML
    private GridPane gridPrincipal;
    @FXML
    Menu clientes;
    @FXML
    Menu usuarios;
    @FXML
    Menu produccion;
    @FXML
    Menu productos;
    @FXML
    Menu proveedores;
    @FXML
    Menu recetas;
    @FXML
    MenuItem miCerrarSesion;
    @FXML
    MenuItem gestionProveedores;
    @FXML
    MenuItem listadoPoveedores;
    @FXML
    MenuItem stock;
    @FXML
    MenuItem entradasProductos;
    @FXML
    MenuItem gestionProductos;
    @FXML
    MenuItem gestionClientes;
    @FXML
    MenuItem gestionRecetas;
    @FXML
    MenuItem listadoClientes;
    @FXML
    MenuItem listadoRecetas;
    @FXML
    MenuItem gestionUsuarios;
    @FXML
    MenuItem stockProducciones;
    private GridPane panelInf;

    public void setNode(Node node){
        System.out.println(gridPrincipal.getChildren().size());
        if(gridPrincipal.getChildren().size() > 1){
            gridPrincipal.getChildren().remove(1);
            gridPrincipal.add(node, 0, 1);
        }else{
            gridPrincipal.add(node, 0, 1);
        }
    }

    //Gestion de los cambios del paner inferior al clicar en los submenus del menu superior
    @FXML
    private void clcickOpcion(ActionEvent event) throws IOException {
        Object evt = event.getSource();
        if(evt.equals(listadoPoveedores)){
            createPage("lista-proveedores-view.fxml", "Proveedores");
        }else if(evt.equals(gestionProveedores)){
            createPage("gestion-proveedores-view.fxml", "Proveedores");
        }else if(evt.equals(stock)){
            createPage("stock-productos-view.fxml", "Productos");
        }else if(evt.equals(entradasProductos)){
            createPage("entrada-productos-view.fxml", "Productos");
        }else if(evt.equals(gestionProductos)){
            createPage("gestion-productos-view.fxml", "Productos");
        }else if(evt.equals(gestionUsuarios)){
            createPage("gestion-usuarios-view.fxml", "Usuarios");
        }else if(evt.equals(gestionClientes)){
            createPage("gestion-clientes-view.fxml", "Clientes");
        }else if(evt.equals(listadoClientes)){
            createPage("lista-clientes-view.fxml", "Clientes");
        }else if(evt.equals(listadoRecetas)){
            createPage("lista-recetas-view.fxml", "Recetas");
        }else if(evt.equals(gestionRecetas)){
            createPage("gestion-recetas-view.fxml", "Recetas");
        }else if(evt.equals(stockProducciones)){
            createPage("stock-producciones-view.fxml", "Producción");
        }else if(evt.equals(miCerrarSesion)) {
            mostrarAlertConfirmacionCerrarSesion(new ActionEvent(), "¿Quiere cerrar su sesión?");
        }
    }


    public void createPage(String fxml, String encabezado) {
        try {
            panelInf = FXMLLoader.load(getClass().getResource("/com/tfg/trazapp/" + fxml));
            setNode(panelInf);
            Stage principal = (Stage) gridPrincipal.getScene().getWindow();
            principal.setTitle("trazApp - " + encabezado);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public ScenesController getSc() {
        return sc;
    }
    public void setSc(ScenesController sc) {
        this.sc = sc;
    }

    @FXML
    private void mostrarAlertConfirmacionCerrarSesion(ActionEvent event, String mensaje) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Información");
        alert.setContentText(mensaje);
        Optional<ButtonType> action = alert.showAndWait();
        if(action.get() == ButtonType.OK){
            sc.cargarLogin();
        }
    }

}