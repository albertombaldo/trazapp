package com.tfg.trazapp.controller;

import com.tfg.trazapp.model.dao.ClienteDAO;
import com.tfg.trazapp.model.dao.EnvioDAO;
import com.tfg.trazapp.model.dao.ProductoFinalDAO;
import com.tfg.trazapp.model.dto.EnvioDTO;
import com.tfg.trazapp.model.vo.Cliente;
import com.tfg.trazapp.model.vo.ProductoFinal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

    //VENTANA GENERAR ENVIO
    @FXML
    private ComboBox<String> cbCliente;
    @FXML
    private ComboBox<String> cbProducto;
    @FXML
    private TableColumn colCantidadProductoEnvio;
    @FXML
    private TableColumn colLoteProductoEnvio;
    @FXML
    private TableColumn colNombreProductoEnvio;
    @FXML
    private Label labelFechaCad;
    @FXML
    private Label labelNumAlbaran;
    @FXML
    private TableView<?> listaProductos;
    @FXML
    private TextField tfUnidades;

    private ObservableList<EnvioDTO> envios = FXCollections.observableArrayList();
    private ObservableList<String> nombresProductosFinales = obtenerNombresProductosFinales().sorted();
    private ObservableList<String> nombresClientes = obtenerNombresClientes().sorted();

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
        }else if(listaProductos != null){
            listaProductos.setPlaceholder(new Label(""));
            this.colLoteProductoEnvio.setCellValueFactory(new PropertyValueFactory("lote_producto"));
            this.colNombreProductoEnvio.setCellValueFactory(new PropertyValueFactory("nombre_producto"));
            this.colCantidadProductoEnvio.setCellValueFactory(new PropertyValueFactory("cantidad_producto"));
            this.cbProducto.setItems(nombresProductosFinales);
            this.cbCliente.setItems(nombresClientes);
            //Seteamos el loteado con epoch para saber la fecha de envio y que no se repitan los albaranes
            labelNumAlbaran.setText(String.valueOf(LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(1))));
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
    public void anadirAPedido(ActionEvent actionEvent) {

    }
    public void alta(ActionEvent actionEvent) {

    }

    public void anadirProductoAEnvio(){

    }

    /**
     * Castea un JSON a un objeto de tipo ProductoFinal
     * @param jsonproductos
     * @return ProductoFinal
     */
    public ProductoFinal getProductoFinal(JSONObject jsonproductos){
        Long id = Long.parseLong(jsonproductos.get("id_producto_final").toString());
        String nombre = jsonproductos.get("nombre").toString();
        float pesoUnidad = Float.parseFloat(jsonproductos.get("peso_por_unidad").toString());
        Long udsPaquete = Long.parseLong(jsonproductos.get("unidades_por_paquete").toString());
        Long paquetesCaja = Long.parseLong(jsonproductos.get("paquetes_por_caja").toString());

        return new ProductoFinal(id, nombre, pesoUnidad, udsPaquete, paquetesCaja);
    }

    /**
     * Castea un JSON a un objeto de tipo Cliente
     * @param jsonclientes
     * @return Cliente
     */
    public Cliente getCliente(JSONObject jsonclientes){
        Long id = Long.parseLong(jsonclientes.get("id_cliente").toString());
        String nombre = jsonclientes.get("nombre").toString();
        String nif = jsonclientes.get("nif").toString();
        String tel = jsonclientes.get("telefono").toString();
        String dir = jsonclientes.get("direccion").toString();

        return new Cliente(id, nombre, nif, tel, dir);
    }

    /**
     * Hace una llamada a la API para obtener todos los tipos de productos finales e inserta sus nombres en una ObservableList<String>
     * @return ObservableList<String> con los nombres de los productos finales de la base de datos
     */
    public ObservableList<String> obtenerNombresProductosFinales(){
        ObservableList<String> prods = FXCollections.observableArrayList();
        JSONArray arrayProductos = new ProductoFinalDAO().getAllProductos();
        for(int i = 0 ; i < arrayProductos.length(); i++){
            prods.add(getProductoFinal(arrayProductos.getJSONObject(i)).getNombre());
        }
        return prods;
    }

    /**
     * Hace una llamada a la API para obtener todos los clientes e inserta sus nombres en una ObservableList<String>
     * @return ObservableList<String> con los nombres de los clientes de la base de datos
     */
    public ObservableList<String> obtenerNombresClientes(){
        ObservableList<String> clis = FXCollections.observableArrayList();
        JSONArray arrayClientes = new ClienteDAO().getAllClientes();
        for(int i = 0 ; i < arrayClientes.length(); i++){
            clis.add(getCliente(arrayClientes.getJSONObject(i)).getNombre());
        }
        return clis;
    }

}
