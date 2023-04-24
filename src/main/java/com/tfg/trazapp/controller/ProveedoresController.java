package com.tfg.trazapp.controller;

import com.tfg.trazapp.model.dao.ProveedorDAO;
import com.tfg.trazapp.model.vo.Proveedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.json.JSONArray;

import java.net.URL;
import java.util.ResourceBundle;

public class ProveedoresController implements Initializable {

    //VENTANA DE LISTADO
    @FXML
    private Button btnFiltrar;
    @FXML
    private TextField tfNombreProv;
    @FXML
    private TableView<Proveedor> listaProveedores;
    @FXML
    private TableColumn colIdProveedor;
    @FXML
    private TableColumn colNombreProveedor;
    @FXML
    private TableColumn colDirProveedor;
    @FXML
    private TableColumn colTelProveedor;
    @FXML
    private TableColumn colNifProveedor;

    //VENTANA DE GESTION (La lista la comparte con la ventana de listado)
    @FXML
    private TextField tfDireccion;
    @FXML
    private TextField tfNif;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfTelefono;
    private ObservableList<Proveedor> proveedores = FXCollections.observableArrayList();
    private Proveedor p;
    private ProveedorDAO pdao = new ProveedorDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(this.listaProveedores != null){
            this.colIdProveedor.setCellValueFactory(new PropertyValueFactory("id"));
            this.colNombreProveedor.setCellValueFactory(new PropertyValueFactory("nombre"));
            this.colNifProveedor.setCellValueFactory(new PropertyValueFactory("nif"));
            this.colTelProveedor.setCellValueFactory(new PropertyValueFactory("telefono"));
            this.colDirProveedor.setCellValueFactory(new PropertyValueFactory("direccion"));

            mostrarListaProveedores(new ProveedorDAO().getAllProveedores());
        }
    }
    @FXML
    private void clickAction(ActionEvent event){
        Object evt = event.getSource();
        JSONArray provs;
        if(evt.equals(btnFiltrar)){
            if(tfNombreProv .getText().equals("")){
                provs = pdao.getAllProveedores();
                if(provs.length() > 0)
                    mostrarListaProveedores(provs);
            }else{
                provs = new ProveedorDAO().getProveedoresPorNombre(tfNombreProv.getText());
                if(provs.length() > 0){
                    mostrarListaProveedores(provs);
                }else{
                    this.listaProveedores.getItems().clear();
                    listaProveedores.setPlaceholder(new Label("No se han encontrado resultados para su búsqueda"));
                }
            }
        }
    }

    @FXML
    public void mostrarListaProveedores(JSONArray jsonproveedores){
        this.listaProveedores.getItems().clear();
        for(int i = 0; i<jsonproveedores.length(); i++){
            Long id = Long.parseLong(jsonproveedores.getJSONObject(i).get("id_proveedor").toString());
            String nombre = jsonproveedores.getJSONObject(i).get("nombre").toString();
            String nif = jsonproveedores.getJSONObject(i).get("nif").toString();
            String telefono = jsonproveedores.getJSONObject(i).get("telefono").toString();
            String direccion = jsonproveedores.getJSONObject(i).get("direccion").toString();

            proveedores.add(new Proveedor(id, nombre, nif, telefono, direccion));
        }
        this.listaProveedores.setItems(proveedores);
    }

    public void seleccionar(MouseEvent mouseEvent) {
        p = this.listaProveedores.getSelectionModel().getSelectedItem();
        if(p != null){
            this.tfNombre.setText(p.getNombre());
            this.tfNif.setText(p.getNif());
            this.tfTelefono.setText(p.getTelefono());
            this.tfDireccion.setText(p.getDireccion());
        }
    }
    public void modificar(ActionEvent actionEvent) {
        if (p == null){
            mostrarAlertError(new ActionEvent(), "Debe rellenar todos los campos");
        }else{
            Proveedor nuevo = new Proveedor(p.getId(), tfNombre.getText(), tfNif.getText(),tfTelefono.getText(), tfDireccion.getText());
            pdao.modificarProveedor(nuevo);
            mostrarListaProveedores(new ProveedorDAO().getAllProveedores());
        }
    }

    public void alta(ActionEvent actionEvent) {
        if (tfNombre.getText().equals("") || tfNif.getText().equals("") || tfTelefono.getText().equals("") || tfDireccion.getText().equals("")){
            mostrarAlertError(new ActionEvent(), "Debe rellenar todos los campos");
        }else{
            Proveedor nuevo = new Proveedor(0l, tfNombre.getText(), tfNif.getText(),tfTelefono.getText(), tfDireccion.getText());
            pdao.anadirProveedor(nuevo);
            mostrarListaProveedores(new ProveedorDAO().getAllProveedores());
        }
    }

    public void eliminar(ActionEvent actionEvent) {
        if (p == null){
            mostrarAlertError(new ActionEvent(), "Debe seleccionar un proveedor");
        }else{
            pdao.deleteProveedor(p);
            mostrarListaProveedores(new ProveedorDAO().getAllProveedores());
        }
    }

    private void mostrarAlertError(ActionEvent event, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAlertOk(ActionEvent event, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Información");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
