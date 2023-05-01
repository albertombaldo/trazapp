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
            createPageListaProveedores();
        }else if(evt.equals(gestionProveedores)){
            createPageGestionProveedores();
        }else if(evt.equals(stock)){
            createPageStock();
        }else if(evt.equals(entradasProductos)){
            createPageEntradaProductos();
        }else if(evt.equals(miCerrarSesion)){
            mostrarAlertConfirmacionCerrarSesion(new ActionEvent(), "¿Quiere cerrar su sesión?");
        }else if(evt.equals(gestionProductos)){
            createPageGestionProductos();
        }else if(evt.equals(gestionUsuarios)){
            createPageGestionUsuarios();
        }else if(evt.equals(gestionClientes)){
            createPageGestionClientes();
        }else if(evt.equals(listadoClientes)){
            createPageListaClientes();
        }else if(evt.equals(listadoRecetas)){
            createPageListaRecetas();
        }else if(evt.equals(gestionRecetas)){
            createPageGestionRecetas();
        }
    }

    public void createPageListaProveedores() {
        try {
            panelInf = FXMLLoader.load(getClass().getResource("/com/tfg/trazapp/lista-proveedores-view.fxml"));
            setNode(panelInf);
            Stage principal = (Stage) gridPrincipal.getScene().getWindow();
            principal.setTitle("trazApp - Proveedores");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void createPageGestionProveedores() {
        try {
            panelInf = FXMLLoader.load(getClass().getResource("/com/tfg/trazapp/gestion-proveedores-view.fxml"));
            setNode(panelInf);
            Stage principal = (Stage) gridPrincipal.getScene().getWindow();
            principal.setTitle("trazApp - Proveedores");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createPageStock() {
        try {
            panelInf = FXMLLoader.load(getClass().getResource("/com/tfg/trazapp/stock-productos-view.fxml"));
            setNode(panelInf);
            Stage principal = (Stage) gridPrincipal.getScene().getWindow();
            principal.setTitle("trazApp - Productos");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void createPageEntradaProductos() {
        try {
            panelInf = FXMLLoader.load(getClass().getResource("/com/tfg/trazapp/entrada-productos-view.fxml"));
            setNode(panelInf);
            Stage principal = (Stage) gridPrincipal.getScene().getWindow();
            principal.setTitle("trazApp - Productos");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createPageGestionProductos() {
        try {
            panelInf = FXMLLoader.load(getClass().getResource("/com/tfg/trazapp/gestion-productos-view.fxml"));
            setNode(panelInf);
            Stage principal = (Stage) gridPrincipal.getScene().getWindow();
            principal.setTitle("trazApp - Productos");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createPageGestionUsuarios() {
        try {
            panelInf = FXMLLoader.load(getClass().getResource("/com/tfg/trazapp/gestion-usuarios-view.fxml"));
            setNode(panelInf);
            Stage principal = (Stage) gridPrincipal.getScene().getWindow();
            principal.setTitle("trazApp - Usuarios");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createPageGestionClientes() {
        try {
            panelInf = FXMLLoader.load(getClass().getResource("/com/tfg/trazapp/gestion-clientes-view.fxml"));
            setNode(panelInf);
            Stage principal = (Stage) gridPrincipal.getScene().getWindow();
            principal.setTitle("trazApp - Clientes");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createPageListaClientes() {
        try {
            panelInf = FXMLLoader.load(getClass().getResource("/com/tfg/trazapp/lista-clientes-view.fxml"));
            setNode(panelInf);
            Stage principal = (Stage) gridPrincipal.getScene().getWindow();
            principal.setTitle("trazApp - Clientes");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createPageGestionRecetas() {
        try {
            panelInf = FXMLLoader.load(getClass().getResource("/com/tfg/trazapp/gestion-recetas-view.fxml"));
            setNode(panelInf);
            Stage principal = (Stage) gridPrincipal.getScene().getWindow();
            principal.setTitle("trazApp - Recetas");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createPageListaRecetas() {
        try {
            panelInf = FXMLLoader.load(getClass().getResource("/com/tfg/trazapp/lista-recetas-view.fxml"));
            setNode(panelInf);
            Stage principal = (Stage) gridPrincipal.getScene().getWindow();
            principal.setTitle("trazApp - Recetas");
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