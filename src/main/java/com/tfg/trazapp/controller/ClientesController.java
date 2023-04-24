package com.tfg.trazapp.controller;

import com.tfg.trazapp.model.dao.ClienteDAO;
import com.tfg.trazapp.model.vo.Cliente;
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

public class ClientesController implements Initializable {

    //VENTANA DE LISTADO
    @FXML
    private Button btnFiltrar;
    @FXML
    private TextField tfNombreCliente;
    @FXML
    private TableView<Cliente> listaClientes;
    @FXML
    private TableColumn colIdCliente;
    @FXML
    private TableColumn colNombreCliente;
    @FXML
    private TableColumn colDirCliente;
    @FXML
    private TableColumn colTelCliente;
    @FXML
    private TableColumn colNifCliente;

    //VENTANA DE GESTION (La lista la comparte con la ventana de listado)
    @FXML
    private TextField tfDireccion;
    @FXML
    private TextField tfNif;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfTelefono;
    private ObservableList<Cliente> clientes = FXCollections.observableArrayList();
    private Cliente c;
    private ClienteDAO cdao = new ClienteDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(this.listaClientes != null){
            this.colIdCliente.setCellValueFactory(new PropertyValueFactory("id"));
            this.colNombreCliente.setCellValueFactory(new PropertyValueFactory("nombre"));
            this.colNifCliente.setCellValueFactory(new PropertyValueFactory("nif"));
            this.colTelCliente.setCellValueFactory(new PropertyValueFactory("telefono"));
            this.colDirCliente.setCellValueFactory(new PropertyValueFactory("direccion"));

            mostrarListaClientes(new ClienteDAO().getAllClientes());
        }
    }
    @FXML
    private void clickAction(ActionEvent event){
        Object evt = event.getSource();
        JSONArray clis;
        if(evt.equals(btnFiltrar)){
            if(tfNombreCliente .getText().equals("")){
                clis = cdao.getAllClientes();
                if(clis.length() > 0)
                    mostrarListaClientes(clis);
            }else{
                clis = new ClienteDAO().getClientePorNombre(tfNombreCliente.getText());
                if(clis.length() > 0){
                    mostrarListaClientes(clis);
                }else{
                    this.listaClientes.getItems().clear();
                    listaClientes.setPlaceholder(new Label("No se han encontrado resultados para su búsqueda"));
                }
            }
        }
    }

    @FXML
    public void mostrarListaClientes(JSONArray jsonclientes){
        this.listaClientes.getItems().clear();
        for(int i = 0; i<jsonclientes.length(); i++){
            Long id = Long.parseLong(jsonclientes.getJSONObject(i).get("id_cliente").toString());
            String nombre = jsonclientes.getJSONObject(i).get("nombre").toString();
            String nif = jsonclientes.getJSONObject(i).get("nif").toString();
            String telefono = jsonclientes.getJSONObject(i).get("telefono").toString();
            String direccion = jsonclientes.getJSONObject(i).get("direccion").toString();

            clientes.add(new Cliente(id, nombre, nif, telefono, direccion));
        }
        this.listaClientes.setItems(clientes);
    }

    public void seleccionar(MouseEvent mouseEvent) {
        c = this.listaClientes.getSelectionModel().getSelectedItem();
        if(c != null){
            this.tfNombre.setText(c.getNombre());
            this.tfNif.setText(c.getNif());
            this.tfTelefono.setText(c.getTelefono());
            this.tfDireccion.setText(c.getDireccion());
        }
    }
    public void modificar(ActionEvent actionEvent) {
        if (c == null){
            mostrarAlertError(new ActionEvent(), "Debe rellenar todos los campos");
        }else{
            Cliente nuevo = new Cliente(c.getId(), tfNombre.getText(), tfNif.getText(),tfTelefono.getText(), tfDireccion.getText());
            cdao.modificarCliente(nuevo);
            mostrarListaClientes(new ClienteDAO().getAllClientes());
        }
    }

    public void alta(ActionEvent actionEvent) {
        if (tfNombre.getText().equals("") || tfNif.getText().equals("") || tfTelefono.getText().equals("") || tfDireccion.getText().equals("")){
            mostrarAlertError(new ActionEvent(), "Debe rellenar todos los campos");
        }else{
            Cliente nuevo = new Cliente(0l, tfNombre.getText(), tfNif.getText(),tfTelefono.getText(), tfDireccion.getText());
            cdao.anadirCliente(nuevo);
            mostrarListaClientes(new ClienteDAO().getAllClientes());
        }
    }

    public void eliminar(ActionEvent actionEvent) {
        if (c == null){
            mostrarAlertError(new ActionEvent(), "Debe seleccionar un cliente");
        }else{
            cdao.deleteCliente(c);
            mostrarListaClientes(new ClienteDAO().getAllClientes());
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
