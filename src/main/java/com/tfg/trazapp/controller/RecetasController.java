package com.tfg.trazapp.controller;

import com.tfg.trazapp.model.dao.ProductoDAO;
import com.tfg.trazapp.model.dao.RecetaDAO;
import com.tfg.trazapp.model.dto.UtilizaDTO;
import com.tfg.trazapp.model.dto.UtilizaDTOComboBox;
import com.tfg.trazapp.model.vo.Producto;
import com.tfg.trazapp.model.vo.Receta;
import com.tfg.trazapp.model.vo.Utiliza;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class RecetasController implements Initializable{

    //MENU GESTION
    @FXML
    private Button btnAnadir;
    @FXML
    private Button btnGuardarReceta;
    @FXML
    private TableColumn colCantidad;
    @FXML
    private TableColumn colIdReceta;
    @FXML
    private TableColumn colNombreReceta;
    @FXML
    private TableColumn colProducto;
    @FXML
    private TableView<UtilizaDTOComboBox> listaEntradaProductos;
    @FXML
    private TableView<Receta> listaEntradaProductos1;
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
    private TableView<Receta> listaRecetasListado;
    @FXML
    private TextField tfNombreRecetaListado;

    private ObservableList<Receta> recetas = FXCollections.observableArrayList();
    private ObservableList<UtilizaDTO> consumos = FXCollections.observableArrayList();
    private ObservableList<String> nombresProductos = obtenerNombresProductos().sorted();
    private ObservableList<UtilizaDTOComboBox> altaCantidades = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(this.listaRecetasListado != null){
            this.colIdRecetaListado.setCellValueFactory(new PropertyValueFactory("id_receta"));
            this.colNombreRecetaListado.setCellValueFactory(new PropertyValueFactory("nombre"));
            this.colNombreProductoListado.setCellValueFactory(new PropertyValueFactory("producto"));
            this.colCantidadProductoListado.setCellValueFactory(new PropertyValueFactory("cantidad_mp"));
            mostrarListaRecetas(new RecetaDAO().getAllRecetas());
        }else if(this.listaEntradaProductos1 != null){
            this.colIdReceta.setCellValueFactory(new PropertyValueFactory("id_receta"));
            this.colNombreReceta.setCellValueFactory(new PropertyValueFactory("nombre"));
            this.colProducto.setCellValueFactory(new PropertyValueFactory("producto"));
            this.colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad_mp"));
            this.colCantidad.setCellFactory(TextFieldTableCell.forTableColumn());
            mostrarRecetas(new RecetaDAO().getAllRecetas());
        }
    }

    @FXML
    public void mostrarRecetas(JSONArray jsonrecetas){
        this.listaEntradaProductos1.getItems().clear();
        for(int i = 0; i<jsonrecetas.length(); i++){
            Long id = Long.parseLong(jsonrecetas.getJSONObject(i).get("id_receta").toString());
            String nombre = jsonrecetas.getJSONObject(i).get("nombre").toString();
            recetas.add(new Receta(id, nombre));
        }
        this.listaEntradaProductos1.setItems(recetas);
    }

    @FXML
    public void mostrarListaRecetas(JSONArray jsonrecetas){
        this.listaRecetasListado.getItems().clear();
        for(int i = 0; i<jsonrecetas.length(); i++){
            Long id = Long.parseLong(jsonrecetas.getJSONObject(i).get("id_receta").toString());
            String nombre = jsonrecetas.getJSONObject(i).get("nombre").toString();
            recetas.add(new Receta(id, nombre));
        }
        this.listaRecetasListado.setItems(recetas);
    }

    public void mostrarListaConsumos(JSONArray jsonconsumos){
        this.listaProductosListado.getItems().clear();
        for(int i = 0; i<jsonconsumos.length(); i++){
            Long id = Long.parseLong(jsonconsumos.getJSONObject(i).get("id_uso").toString());
            JSONObject producto = (JSONObject) jsonconsumos.getJSONObject(i).get("producto");
            String prod = producto.get("nombre").toString();
            Float cantidad = Float.parseFloat(jsonconsumos.getJSONObject(i).get("cantidad_mp").toString());
            consumos.add(new UtilizaDTO(id, prod, cantidad));
        }
        this.listaProductosListado.setItems(consumos);
    }

    public void mostrarListaEntradas(JSONArray jsonconsumos){
        this.altaCantidades.clear();
        for(int i = 0; i<jsonconsumos.length(); i++){
            ComboBox prod = new ComboBox<>();
            prod.setItems(nombresProductos);
            JSONObject producto = (JSONObject) jsonconsumos.getJSONObject(i).get("producto");
            prod.setValue(producto.get("nombre").toString());
            String cantidad = jsonconsumos.getJSONObject(i).get("cantidad_mp").toString();
            altaCantidades.add(new UtilizaDTOComboBox(prod, cantidad));
        }
        this.listaEntradaProductos.setItems(altaCantidades);
    }

    public void seleccionar(MouseEvent mouseEvent) {
        if(listaRecetasListado != null){
            Receta rdto = this.listaRecetasListado.getSelectionModel().getSelectedItem();
            JSONArray materiasPrimas = new RecetaDAO().getUsos(rdto.getId_receta());
            labelNombreReceta.setText(rdto.getNombre());
            mostrarListaConsumos(materiasPrimas);
        }else{
            Receta rdtoEntrada = this.listaEntradaProductos1.getSelectionModel().getSelectedItem();
            JSONArray materiasPrimas = new RecetaDAO().getUsos(rdtoEntrada.getId_receta());
            tfNombreReceta.setText(rdtoEntrada.getNombre());
            mostrarListaEntradas(materiasPrimas);
        }
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

    @FXML
    void anadirRegistro(ActionEvent event) {
        ComboBox productos = new ComboBox<>();
        productos.setItems(nombresProductos);
        altaCantidades.add(new UtilizaDTOComboBox(productos, ""));
        listaEntradaProductos.setItems(altaCantidades);
    }

    @FXML
    void guardarReceta(ActionEvent event) {
        Receta receta = new Receta(0l, tfNombreReceta.getText());
        new RecetaDAO().anadirReceta(receta);
        JSONObject recetaconId = (JSONObject) new RecetaDAO().getRecetaPorNombre(tfNombreReceta.getText()).get(0);
        receta.setId_receta(Long.parseLong(recetaconId.get("id_receta").toString()));
        for(int i = 0; i < listaEntradaProductos.getItems().size(); i++){
            UtilizaDTOComboBox udtocb = listaEntradaProductos.getItems().get(i);
            JSONObject producto = new ProductoDAO().getProductoPorNombre(StringUtils.stripAccents(udtocb.getProducto().getValue().toString()).replaceAll(" ", "%20")).getJSONObject(0);
            Producto p = new Producto(Long.parseLong(producto.get("id_producto").toString()), producto.get("nombre").toString(), producto.get("tipo").toString());
            System.out.println(udtocb.getCantidad_mp());
            float cantidad = Float.parseFloat(udtocb.getCantidad_mp());
            new RecetaDAO().anadirUso(new Utiliza(0l,receta,p, cantidad));
        }

    }

    @FXML
    void clickAction(ActionEvent event) {
        Object evt = event.getSource();
        JSONArray recs;
        if(evt.equals(btnFiltrarListado)){
            if(tfNombreRecetaListado.getText().equals("")){
                recs = new RecetaDAO().getAllRecetas();
                if(recs.length() > 0)
                    mostrarListaRecetas(recs);
            }else{
                recs = new RecetaDAO().getRecetaPorNombre(tfNombreRecetaListado.getText());
                if(recs.length() > 0){
                    mostrarListaRecetas(recs);
                }else{
                    this.listaRecetasListado.getItems().clear();
                    listaRecetasListado.setPlaceholder(new Label("No se han encontrado resultados para su búsqueda"));
                }
            }
        }
    }

    @FXML
    void onEditChanged(TableColumn.CellEditEvent<UtilizaDTOComboBox, String> cellEditEvent) {
        UtilizaDTOComboBox editado = listaEntradaProductos.getSelectionModel().getSelectedItem();
        if(listaEntradaProductos.getEditingCell().getColumn() == 1){
            editado.setCantidad_mp(cellEditEvent.getNewValue());
        }
    }
}