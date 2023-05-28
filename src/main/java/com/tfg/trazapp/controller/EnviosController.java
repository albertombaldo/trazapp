package com.tfg.trazapp.controller;

import com.tfg.trazapp.model.dao.*;
import com.tfg.trazapp.model.dto.ConsumeDTO;
import com.tfg.trazapp.model.dto.EnvioDTO;
import com.tfg.trazapp.model.dto.EnvioDTOCajas;
import com.tfg.trazapp.model.vo.Cliente;
import com.tfg.trazapp.model.vo.Envio;
import com.tfg.trazapp.model.vo.Produccion;
import com.tfg.trazapp.model.vo.ProductoFinal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
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
    private TableView<EnvioDTOCajas> listaProductos;
    @FXML
    private TextField tfUnidades;

    private ObservableList<EnvioDTO> envios = FXCollections.observableArrayList();
    private ObservableList<String> nombresProductosFinales = obtenerNombresProductosFinales().sorted();
    private ObservableList<String> nombresClientes = obtenerNombresClientes().sorted();
    private ObservableList<EnvioDTOCajas> cajasPedido = FXCollections.observableArrayList();

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
        anadirProductoAEnvio();
    }
    public void anadirAPedido(ActionEvent actionEvent) {
        anadirProductoAEnvio();
    }
    public void alta(ActionEvent actionEvent) {
        if(listaProductos.getItems() != null){
            for(EnvioDTOCajas edtoc : listaProductos.getItems()){
                //Se ajusta el stock
                Produccion p = getProduccion(new ProduccionDAO().getProduccion(edtoc.getLote_producto()).getJSONObject(0));
                p.setStock(p.getStock()-edtoc.getCantidad_producto());
                new ProduccionDAO().anadirProduccion(p);
                Date fechaActual = Date.valueOf(LocalDate.now());
                //Se genera una linea para el envio
                ProductoFinal pf = getProductoFinal(new ProductoFinalDAO().getProductoFinalPorNombre(StringUtils.stripAccents(edtoc.getNombre_producto()).replaceAll(" ", "%20")).getJSONObject(0));
                Cliente c = getCliente(new ClienteDAO().getClientePorNombre(StringUtils.stripAccents(cbCliente.getValue()).replaceAll(" ", "%20")).getJSONObject(0));
                Envio e = new Envio(0l, pf, c, fechaActual, Float.parseFloat(edtoc.getCantidad_producto().toString()), edtoc.getLote_producto().toString(), labelNumAlbaran.getText());
                new EnvioDAO().anadirEnvio(e);
            }
            listaProductos.getItems().clear();
            cajasPedido.clear();
            tfUnidades.setText("");
            labelNumAlbaran.setText(String.valueOf(LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(1))));

        }else{
            mostrarAlertError(new ActionEvent(), "Debe añadir productos s la lista");
        }
    }

    public void anadirProductoAEnvio(){
        if(cbCliente.getValue() != null && cbProducto.getValue() != null && !tfUnidades.getText().equals("")){
            String nombreProd = cbProducto.getValue();
            Long unidades = Long.parseLong(tfUnidades.getText());
            if(!hayStock(nombreProd, unidades)){
                mostrarAlertError(new ActionEvent(), "No hay stock suficiente para completar el pedido");
            }else{
                JSONArray producciones = new ProduccionDAO().getProduccionesPorProductoYFechaAsc(new ProductoFinalDAO().getProductoFinalPorNombre(StringUtils.stripAccents(nombreProd).replaceAll(" ", "%20")).getJSONObject(0).getLong("id_producto_final"));
                anadirCajasALista(producciones, unidades);
            }
        }else{
            mostrarAlertError(new ActionEvent(), "Debe rellenar todos los campos");
        }
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
     * Castea un JSON a un objeto de tipo Produccion
     * @param jsonproduccion
     * @return ProductoFinal
     */
    public Produccion getProduccion(JSONObject jsonproduccion){
        String lote = jsonproduccion.get("lote_produccion").toString();
        ProductoFinal pf = getProductoFinal(jsonproduccion.getJSONObject("producto_final"));
        Date fechaCad = Date.valueOf(jsonproduccion.get("fecha_caducidad").toString());
        Date fechaProd = Date.valueOf(jsonproduccion.get("fecha_produccion").toString());
        Long uds = Long.parseLong(jsonproduccion.get("unidades").toString());
        Long stock = Long.parseLong(jsonproduccion.get("stock").toString());

        return new Produccion(lote, pf, fechaProd, fechaCad, uds, stock);
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

    /**
     * Comprueba si hay unidades suficuentes para completar el pedido
     * @param nombre
     * @param udsNecesarias
     * @return boolean
     */
    public boolean hayStock(String nombre, Long udsNecesarias){
        boolean hayStock = false;
        JSONArray producciones = new ProduccionDAO().getProduccionesPorProducto(new ProductoFinalDAO().getProductoFinalPorNombre(StringUtils.stripAccents(nombre).replaceAll(" ", "%20")).getJSONObject(0).getLong("id_producto_final"));
        Long stockDisponible = 0l;
        for(int i =0; i<producciones.length(); i++){
            stockDisponible = stockDisponible + (producciones.getJSONObject(0).getLong("stock"));
        }
        if((stockDisponible-udsNecesarias)>=0)
            hayStock = true;
        return hayStock;
    }

    /**
     * Añade a la lista del pedido las cajas de un producto
     * @param stocks
     * @param udsNecesarias
     *
     */
    public void anadirCajasALista(JSONArray stocks, Long udsNecesarias){
        int cont = 0;
        while(udsNecesarias>0){
            if(stocks.getJSONObject(cont).getLong("stock") >= udsNecesarias){
                cajasPedido.add(new EnvioDTOCajas(stocks.getJSONObject(cont).getLong("lote_produccion"), stocks.getJSONObject(cont).getJSONObject("producto_final").getString("nombre"), udsNecesarias));
                udsNecesarias = 0l;
            }else{
                EnvioDTOCajas edtoc = new EnvioDTOCajas(stocks.getJSONObject(cont).getLong("lote_produccion"), stocks.getJSONObject(cont).getJSONObject("producto_final").getString("nombre"), stocks.getJSONObject(cont).getLong("stock"));
                cajasPedido.add(edtoc);
                udsNecesarias = udsNecesarias - edtoc.getCantidad_producto();
                cont++;
            }
        }
        listaProductos.setItems(cajasPedido);
    }

    private void mostrarAlertError(ActionEvent event, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
