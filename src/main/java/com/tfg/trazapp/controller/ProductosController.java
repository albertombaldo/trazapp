package com.tfg.trazapp.controller;

import com.tfg.trazapp.model.dao.ProductoDAO;
import com.tfg.trazapp.model.dao.ProveedorDAO;
import com.tfg.trazapp.model.dao.SuministroDAO;
import com.tfg.trazapp.model.dto.ProductoDTOComboBox;
import com.tfg.trazapp.model.dto.SuministroDTO;
import com.tfg.trazapp.model.vo.Producto;
import com.tfg.trazapp.model.vo.Proveedor;
import com.tfg.trazapp.model.vo.Suministro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import org.apache.commons.lang3.StringUtils;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductosController implements Initializable {

    //VENTANA STOCKS
    @FXML
    private Button btnFiltrarSum;
    @FXML
    private TableView<SuministroDTO> listaSuministros;
    @FXML
    private TableColumn colAlbaran;
    @FXML
    private TableColumn colCantidad;
    @FXML
    private TableColumn colFechaCad;
    @FXML
    private TableColumn colFechaRec;
    @FXML
    private TableColumn colIdSum;
    @FXML
    private TableColumn colLote;
    @FXML
    private TableColumn colNombreProveedorSuministro;
    @FXML
    private TableColumn colProducto;
    @FXML
    private TableColumn colStock;
    @FXML
    private TextField tfAlbaran;

    //VENTANA ENTRADAS
    @FXML
    private Button btnGuardarEnt;
    @FXML
    private Button btnEliminarRegistro;
    @FXML
    private TableColumn colCantidadEnt;
    @FXML
    private TableColumn colFechaCadEnt;
    @FXML
    private TableColumn colLoteEnt;
    @FXML
    private TableColumn colProductoEnt;
    @FXML
    private DatePicker dpFechaEnt;
    @FXML
    private TableView<ProductoDTOComboBox> listaEntradaProductos;
    @FXML
    private TextField tfAlbaranEnt;
    @FXML
    private ComboBox cbProveedorEnt;

    //VENTANA GESTION PRODUCTOS
    @FXML
    private TableView<Producto> listaProductos;
    @FXML
    private Button btnFiltrar;
    @FXML
    private TableColumn colIdProducto;
    @FXML
    private TableColumn colNombreProducto;
    @FXML
    private TableColumn colTipoProducto;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfNombreProd;
    @FXML
    private ComboBox<String> cbTipoProd;
    @FXML
    private ComboBox<String> cbTipoProdFiltrar;

    private ObservableList<SuministroDTO> suministros = FXCollections.observableArrayList();
    private ObservableList<ProductoDTOComboBox> entradaProductos = FXCollections.observableArrayList();
    private ObservableList<String> nombresProductos = obtenerNombresProductos().sorted();
    private ObservableList<String> nombresProveedores = obtenerNombresProveedores().sorted();
    private ObservableList<String> tiposProds = FXCollections.observableArrayList("MP", "ENV", "CAJA");
    private ObservableList<String> tiposProdsFiltro = FXCollections.observableArrayList("MP", "ENV", "CAJA", "Todos");
    private ObservableList<Producto> productos = FXCollections.observableArrayList();

    private Producto p;
    private SuministroDAO sumdao = new SuministroDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(listaSuministros != null) {
            this.colIdSum.setCellValueFactory(new PropertyValueFactory("id_suministro"));
            this.colProducto.setCellValueFactory(new PropertyValueFactory("producto"));
            this.colFechaRec.setCellValueFactory(new PropertyValueFactory("fecha_recepcion"));
            this.colFechaCad.setCellValueFactory(new PropertyValueFactory("fecha_caducidad"));
            this.colNombreProveedorSuministro.setCellValueFactory(new PropertyValueFactory("proveedor"));
            this.colAlbaran.setCellValueFactory(new PropertyValueFactory("albaran"));
            this.colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad_recepcionada"));
            this.colStock.setCellValueFactory(new PropertyValueFactory("cantidad_stock"));
            this.colLote.setCellValueFactory(new PropertyValueFactory("lote_producto"));

            mostrarListaSuministros(new SuministroDAO().getAllSuministros());
        }else if (listaEntradaProductos != null){
            cbProveedorEnt.setItems(nombresProveedores);
            dpFechaEnt.setValue(LocalDate.now());
            this.colCantidadEnt.setCellValueFactory(new PropertyValueFactory("cantidad_recepcionada"));
            this.colCantidadEnt.setCellFactory(TextFieldTableCell.forTableColumn());
            this.colFechaCadEnt.setCellValueFactory(new PropertyValueFactory("fecha_caducidad"));
            this.colLoteEnt.setCellValueFactory(new PropertyValueFactory("lote_producto"));
            this.colLoteEnt.setCellFactory(TextFieldTableCell.forTableColumn());
            this.colProductoEnt.setCellValueFactory(new PropertyValueFactory("producto"));
        }else if(listaProductos != null){
            cbTipoProd.setItems(tiposProds);
            cbTipoProdFiltrar.setItems(tiposProdsFiltro);
            colIdProducto.setCellValueFactory(new PropertyValueFactory("id_producto"));
            colNombreProducto.setCellValueFactory(new PropertyValueFactory("nombre"));
            colTipoProducto.setCellValueFactory(new PropertyValueFactory("tipo"));

            mostrarListaProductos(new ProductoDAO().getAllProductos());
        }
    }

    /**
     * Gestiona las acciones de los botones de la pantalla
     * @param event
     */
    @FXML
    void clickAction(ActionEvent event) {
        Object evt = event.getSource();
        JSONArray resultados;
        if(evt.equals(btnFiltrarSum)){
            if(tfAlbaran.getText().equals("")){
                resultados = sumdao.getAllSuministros();
                if(resultados.length() > 0)
                    mostrarListaSuministros(resultados);
            }else{
                resultados = sumdao.getSuministroPorAlbaran(tfAlbaran.getText());
                if(resultados.length() > 0){
                    mostrarListaSuministros(resultados);
                }else{
                    this.listaSuministros.getItems().clear();
                    listaSuministros.setPlaceholder(new Label("No se han encontrado resultados para su búsqueda"));
                }
            }
        }else if (evt.equals(btnGuardarEnt)){
            altaProductos(getProductosLista());
        }else if(evt.equals(btnFiltrar)){
            if((cbTipoProdFiltrar.getValue() == null || cbTipoProdFiltrar.getValue().equals("Todos")) && tfNombreProd.getText().equals("")){
                mostrarListaProductos(new ProductoDAO().getAllProductos());
            }else if((cbTipoProdFiltrar.getValue() == null || cbTipoProdFiltrar.getValue().equals("Todos")) && !tfNombreProd.getText().equals("")){
                resultados = new ProductoDAO().getProductosPorNombre(tfNombreProd.getText());
                if(resultados.length() > 0){
                    mostrarListaProductos(resultados);
                }else{
                    this.listaProductos.getItems().clear();
                    listaProductos.setPlaceholder(new Label("No se han encontrado resultados para su búsqueda"));
                }
            }else if(cbTipoProdFiltrar.getValue() != null && tfNombreProd.getText().equals("")){
                resultados = new ProductoDAO().getProductosPorTipo(cbTipoProdFiltrar.getValue());
                mostrarListaProductos(resultados);
            }else if(cbTipoProdFiltrar.getValue() != null && !tfNombreProd.getText().equals("")){
                resultados = new ProductoDAO().getProductosPorNombre(tfNombreProd.getText());
                if(resultados.length() > 0){
                    for(int i = 0; i < resultados.length(); i++){
                        if(!resultados.getJSONObject(i).get("tipo").toString().equals(cbTipoProdFiltrar.getValue())){
                            resultados.remove(i);
                        }
                    }
                    mostrarListaProductos(resultados);
                }else{
                    this.listaProductos.getItems().clear();
                    listaProductos.setPlaceholder(new Label("No se han encontrado resultados para su búsqueda"));
                }
            }
        }
    }

    /**
     * Muestra los suministros recepcionados hasta la fecha
     * @param jsonsum
     */
    @FXML
    public void mostrarListaSuministros(JSONArray jsonsum){
        this.listaSuministros.getItems().clear();
        JSONObject producto;
        JSONObject  proveedor;
        for(int i = 0; i<jsonsum.length(); i++){
            Long id = Long.parseLong(jsonsum.getJSONObject(i).get("id_suministro").toString());
            String fecha_recepcion = jsonsum.getJSONObject(i).get("fecha_recepcion").toString();
            String fecha_caducidad = jsonsum.getJSONObject(i).get("fecha_caducidad").toString();
            proveedor = jsonsum.getJSONObject(i).getJSONObject("proveedor");
            Proveedor prov = new Proveedor(0l, proveedor.getString("nombre"), "", "", "");
            producto = jsonsum.getJSONObject(i).getJSONObject("producto");
            Producto prod = new Producto(0l, producto.getString("nombre"), "");
            String albaran = jsonsum.getJSONObject(i).get("albaran").toString();
            float cantRec = jsonsum.getJSONObject(i).getFloat("cantidad_recepcionada");
            float stock = jsonsum.getJSONObject(i).getFloat("cantidad_stock");
            String lote = jsonsum.getJSONObject(i).getString("lote_producto");

            suministros.add(new SuministroDTO(id, fecha_recepcion, fecha_caducidad, prov.getNombre(), prod.getNombre(), albaran, cantRec, stock, lote));
        }
        this.listaSuministros.setItems(suministros);
    }

    /**
     * Muestra los distintos productos
     * @param jsonprod
     */
    @FXML
    public void mostrarListaProductos(JSONArray jsonprod){
        this.listaProductos.getItems().clear();
        for(int i = 0; i<jsonprod.length(); i++){
            Long id = Long.parseLong(jsonprod.getJSONObject(i).get("id_producto").toString());
            String nombre_producto = jsonprod.getJSONObject(i).get("nombre").toString();
            String tipo_producto = jsonprod.getJSONObject(i).get("tipo").toString();

            productos.add(new Producto(id, nombre_producto, tipo_producto));
        }
        this.listaProductos.setItems(productos);
    }

    /**
     * Cambia los datos de las celdas modificadas en la tabla de entrada de albaranes
     * Una vez modificado el campo hay que pulsar Enter para que se fije en la celda
     * @param cellEditEvent
     */
    @FXML
    public void onEditChanged(TableColumn.CellEditEvent<ProductoDTOComboBox, String> cellEditEvent) {
        ProductoDTOComboBox editado = listaEntradaProductos.getSelectionModel().getSelectedItem();
        if(listaEntradaProductos.getEditingCell().getColumn() == 2){
            editado.setCantidad_recepcionada(cellEditEvent.getNewValue());
        }else if(listaEntradaProductos.getEditingCell().getColumn() == 3){
            editado.setLote_producto(cellEditEvent.getNewValue());
        }
    }

    /**
     * Añade una fila a la tabla de entrada de productos de albaranes cuando se clicka sobre ella
     * @param actionEvent
     */
    @FXML
    public void anadirRegistro(ActionEvent actionEvent) {
        ComboBox productos = new ComboBox<>();
        productos.setItems(nombresProductos);
        entradaProductos.add(new ProductoDTOComboBox(null, productos, "", ""));
        listaEntradaProductos.setItems(entradaProductos);
    }

    /**
     * Obtiene los productos de la lista de entrada de albaranes
     * @return ArrayList<Suministro>
     */
    public ArrayList<Suministro> getProductosLista(){
        Suministro s;
        ArrayList<Suministro> als = new ArrayList<>();
        if(comprobarCamposVacios()){
            mostrarMensajeError(new ActionEvent(), "Debe rellenar todos los campos");
        }else{
            try{
                for(int i = 0; i < listaEntradaProductos.getItems().size(); i++){
                    ProductoDTOComboBox pdc = listaEntradaProductos.getItems().get(i);
                    Date fEnt = Date.valueOf(dpFechaEnt.getValue());
                    Date fCad = null;
                    if(pdc.getFecha_caducidad().getValue() != null){
                        fCad = Date.valueOf(pdc.getFecha_caducidad().getValue());
                    }
                    //Se reemplezan los espacios por "%20" y se quitan las tildes para no tener problemas con la consulta a la API
                    Proveedor prov = getProveedor(new ProveedorDAO().getProveedoresPorNombre((StringUtils.stripAccents(cbProveedorEnt.getValue().toString().replaceAll(" ", "%20")))));
                    Producto prod = getProducto(new ProductoDAO().getProductosPorNombre(StringUtils.stripAccents(pdc.getProducto().getValue().toString().replaceAll(" ", "%20"))).getJSONObject(0));
                    String albaran = tfAlbaranEnt.getText();
                    float catnRec = Float.parseFloat(pdc.getCantidad_recepcionada());
                    String lote = pdc.getLote_producto();
                    s = new Suministro(0l, fEnt, fCad, prov, prod, albaran, catnRec, catnRec, lote);
                    als.add(s);
                }
            }catch(NumberFormatException e){
                mostrarMensajeError(new ActionEvent(), "La cantidad recibida no puede contener letras");
            }
        }
        return als;
    }

    /**
     * Añade los productos de la lista de entrada de albaranes a la base de datos
     * @param prodLista
     */
    public void altaProductos(ArrayList<Suministro> prodLista){
        if(prodLista.size()>0){//Comprobamos que la lista posee registros
            for(Suministro s : prodLista){
                sumdao.anadirSumistro(s);
            }
            mostrarMensajeOk(new ActionEvent(), "El albarán ha sido dado de alta correctamente");
            //Reseteamos todos los campos
            listaEntradaProductos.getItems().clear();
            listaEntradaProductos.refresh();
            dpFechaEnt.setValue(LocalDate.now());
            tfAlbaranEnt.setText("");
            cbProveedorEnt.setValue(null);
            cbProveedorEnt.setPromptText("Proveedor");
        }
    }

    /**
     * Castea un JSONArray a un objeto de tipo Proveedor
     * @param jsonproveedores
     * @return Proveedor
     */
    public Proveedor getProveedor(JSONArray jsonproveedores){
        Long id_proveedor = Long.parseLong(jsonproveedores.getJSONObject(0).get("id_proveedor").toString());
        String nombre = jsonproveedores.getJSONObject(0).get("nombre").toString();
        String nif = jsonproveedores.getJSONObject(0).get("nif").toString();
        String telefono = jsonproveedores.getJSONObject(0).get("telefono").toString();
        String direccion = jsonproveedores.getJSONObject(0).get("direccion").toString();

        return new Proveedor(id_proveedor, nombre, nif, telefono, direccion);
    }

    /**
     * Castea un JSON a un objeto de tipo Producto
     * @param jsonproductos
     * @return Producto
     */
    public Producto getProducto(JSONObject jsonproductos){
        Long id = Long.parseLong(jsonproductos.get("id_producto").toString());
        String nombre = jsonproductos.get("nombre").toString();
        String tipo = jsonproductos.get("tipo").toString();

        return new Producto(id, nombre, tipo);
    }

    /**
     * Castea un JSONObject a un objeto de tipo Proveedor
     * @param jsonproveedor
     * @return Proveedor
     */
    public Proveedor getProveedor(JSONObject jsonproveedor){
        Long id_proveedor = Long.parseLong(jsonproveedor.get("id_proveedor").toString());
        String nombre = jsonproveedor.get("nombre").toString();
        String nif = jsonproveedor.get("nif").toString();
        String telefono = jsonproveedor.get("telefono").toString();
        String direccion = jsonproveedor.get("direccion").toString();

        return new Proveedor(id_proveedor, nombre, nif, telefono, direccion);
    }

    /**
     * Hace una llamada a la API para obtener todos los tipos de productos e inserta sus nombres en una ObservableList<String>
     * @return ObservableList<String> con los nombres de los productos de la base de datos
     */
    public ObservableList<String> obtenerNombresProductos(){
        ObservableList<String> prods = FXCollections.observableArrayList();
        JSONArray productos = new ProductoDAO().getAllProductos();
        for(int i = 0 ; i < productos.length(); i++){
            prods.add(getProducto(productos.getJSONObject(i)).getNombre());
        }
        return prods;
    }

    /**
     * Hace una llamada a la API para obtener todos los proveedores e inserta sus nombres en una ObservableList<String>
     * @return ObservableList<String>
     */
    public ObservableList<String> obtenerNombresProveedores(){
        ObservableList<String> provs = FXCollections.observableArrayList();
        JSONArray proveedores = new ProveedorDAO().getAllProveedores();
        for(int i = 0 ; i < proveedores.length(); i++){
            provs.add(getProveedor(proveedores.getJSONObject(i)).getNombre());
        }
        return provs;
    }

    /**
     * Compueba que se han rellenado todos los datos para dar entrada a un suministro
     * @return boolean
     */
    public boolean comprobarCamposVacios(){
        boolean vacios = false;
        //Primero comprobamos los campos superiores
        if(Date.valueOf(dpFechaEnt.getValue()) == null || tfAlbaranEnt.getText().equals("") || cbProveedorEnt.getValue() == null || cbProveedorEnt.getValue().toString().equals("Proveedor")){
            vacios = true;
        }else{ //Comprobamos todos los campos de los productos de la tabla
            if(listaEntradaProductos.getItems().size() < 1){
                vacios = true;
            }else{
                for(int i = 0; i < listaEntradaProductos.getItems().size(); i++){
                    ProductoDTOComboBox pdc = listaEntradaProductos.getItems().get(i);
                    String cantRec = pdc.getCantidad_recepcionada();
                    String lote = pdc.getLote_producto();
                    if(pdc.getFecha_caducidad() == null || pdc.getProducto() == null || cantRec.equals("") || lote.equals("")){
                        vacios = true;
                    }
                }
            }
        }

        return vacios;
    }

    /**
     * Elimina un registro de tipo Suministro siempre que no haya sido utilizado
     * @param actionEvent
     */
    public void eliminarRegistro(ActionEvent actionEvent) {
        //Comprobamos si el stock restante es igual que el de entrada para ver que no se ha utilizado en producciones
        //Si es agua no se deja eliminar
        if (listaSuministros.getSelectionModel().getSelectedItem() != null) {
            Float cantRep = listaSuministros.getSelectionModel().getSelectedItem().getCantidad_recepcionada();
            Float cantStock = listaSuministros.getSelectionModel().getSelectedItem().getCantidad_stock();
            if(cantStock.equals(cantRep) && !listaSuministros.getSelectionModel().getSelectedItem().getProducto().equals("Agua")){
                sumdao.deleteSuministro(listaSuministros.getSelectionModel().getSelectedItem().getId_suministro());
                listaSuministros.getItems().remove(listaSuministros.getSelectionModel().getSelectedItem());
                listaSuministros.refresh();
            }else{
                mostrarMensajeError(new ActionEvent(), "El suministro no puede eliminarse ya que ha sido empleado en producción");
            }
        }
    }

    /**
     * Muestra un mensaje informando de que la acción ha sido exitosa
     * @param event
     * @param mensaje
     */
    private void mostrarMensajeOk(ActionEvent event, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Información");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    /**
     * Muestra un mensaje informando de que la acción no ha sido exitosa
     * @param event
     * @param mensaje
     */
    private void mostrarMensajeError(ActionEvent event, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    //FUNCIONALIDADES DE LA VENTANA GESTION DE PRODUCTOS
    public void seleccionar(MouseEvent mouseEvent) {
        p = this.listaProductos.getSelectionModel().getSelectedItem();
        if(p != null){
            tfNombre.setText(p.getNombre());
            cbTipoProd.setPromptText(p.getTipo());
        }
    }

    public void modificar(ActionEvent actionEvent) {
        if (p == null){
            mostrarAlertError(new ActionEvent(), "Debe rellenar todos los campos");
        }else{
            Producto nuevo = new Producto(p.getId_producto(), tfNombre.getText(), cbTipoProd.getValue());
            new ProductoDAO().modificarProducto(nuevo);
            mostrarListaProductos(new ProductoDAO().getAllProductos());
        }
    }

    public void alta(ActionEvent actionEvent) {
        if (tfNombre.getText().equals("") || cbTipoProd.getValue().equals("Seleccione el tipo de producto")){
            mostrarAlertError(new ActionEvent(), "Debe rellenar todos los campos");
        }else{
            Producto nuevo = new Producto(0l, tfNombre.getText(), cbTipoProd.getValue());
            new ProductoDAO().anadirProducto(nuevo);
            mostrarListaProductos(new ProductoDAO().getAllProductos());
        }
    }

    public void eliminar(ActionEvent actionEvent) {
        if (p == null){
            mostrarAlertError(new ActionEvent(), "Debe seleccionar un producto");
        }else{
            new ProductoDAO().deleteProducto(p);
            mostrarListaProductos(new ProductoDAO().getAllProductos());
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
