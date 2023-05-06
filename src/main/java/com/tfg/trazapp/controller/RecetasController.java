package com.tfg.trazapp.controller;

import com.tfg.trazapp.model.dao.ClienteDAO;
import com.tfg.trazapp.model.dao.ProductoDAO;
import com.tfg.trazapp.model.dao.RecetaDAO;
import com.tfg.trazapp.model.dto.ProductoDTOComboBox;
import com.tfg.trazapp.model.dto.RecetaDTOSinLista;
import com.tfg.trazapp.model.dto.UtilizaDTO;
import com.tfg.trazapp.model.dto.UtilizaDTOComboBox;
import com.tfg.trazapp.model.vo.Cliente;
import com.tfg.trazapp.model.vo.Producto;
import com.tfg.trazapp.model.vo.Receta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
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
    private TableView<UtilizaDTOComboBox> listaEntradaProductos;
    @FXML
    private TableView<RecetaDTOSinLista> listaEntradaProductos1;
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
            mostrarRecetas(new RecetaDAO().getAllRecetas());
        }
    }

    @FXML
    public void mostrarRecetas(JSONArray jsonrecetas){
        this.listaEntradaProductos1.getItems().clear();
        for(int i = 0; i<jsonrecetas.length(); i++){
            Long id = Long.parseLong(jsonrecetas.getJSONObject(i).get("id_receta").toString());
            String nombre = jsonrecetas.getJSONObject(i).get("nombre").toString();
            recetas.add(new RecetaDTOSinLista(id, nombre));
        }
        this.listaEntradaProductos1.setItems(recetas);
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
            prod.setPromptText(producto.get("nombre").toString());
            String cantidad = jsonconsumos.getJSONObject(i).get("cantidad_mp").toString();
            altaCantidades.add(new UtilizaDTOComboBox(prod, cantidad));
        }
        this.listaEntradaProductos.setItems(altaCantidades);
    }

    public void seleccionar(MouseEvent mouseEvent) {
        if(listaRecetasListado != null){
            RecetaDTOSinLista rdto = this.listaRecetasListado.getSelectionModel().getSelectedItem();
            JSONArray receta = new RecetaDAO().getReceta(rdto.getId_receta());
            JSONArray materiasPrimas = (JSONArray) receta.getJSONObject(0).get("materias_primas");
            labelNombreReceta.setText(rdto.getNombre());
            mostrarListaConsumos(materiasPrimas);
        }else{
            RecetaDTOSinLista rdtoEntrada = this.listaEntradaProductos1.getSelectionModel().getSelectedItem();
            JSONArray receta = new RecetaDAO().getReceta(rdtoEntrada.getId_receta());
            JSONArray materiasPrimas = (JSONArray) receta.getJSONObject(0).get("materias_primas");
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
        }else if(event.equals(btnGuardarReceta)){
            //////////////////////////////
            //GUARDAR LISTA DE PRODUCTOS//
            //////////////////////////////
            ArrayList<Producto> productos = new ArrayList<>();
            Receta receta = new Receta(0l, tfNombreReceta.getText(), productos);
            for(UtilizaDTOComboBox udtocb : listaEntradaProductos.getItems()){
                new RecetaDAO().anadirUso(null);
            }
        }
    }

    @FXML
    void onEditChanged(ActionEvent event) {
    }


}