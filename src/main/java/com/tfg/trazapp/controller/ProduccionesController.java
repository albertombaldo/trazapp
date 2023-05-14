package com.tfg.trazapp.controller;

import com.tfg.trazapp.model.dao.ProduccionDAO;
import com.tfg.trazapp.model.dao.ProductoDAO;
import com.tfg.trazapp.model.dao.ProductoFinalDAO;
import com.tfg.trazapp.model.dao.RecetaDAO;
import com.tfg.trazapp.model.dto.ProductoDTOComboBox;
import com.tfg.trazapp.model.dto.SuministroDTO;
import com.tfg.trazapp.model.dto.UtilizaDTO;
import com.tfg.trazapp.model.vo.Producto;
import com.tfg.trazapp.model.vo.ProductoFinal;
import com.tfg.trazapp.model.vo.Receta;
import com.tfg.trazapp.model.vo.Utiliza;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private ComboBox<String> cbProducto;
    @FXML
    private ComboBox<String> cbReceta;
    @FXML
    private ComboBox<String> cbCaja;
    @FXML
    private ComboBox<String> cbFilm;
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
    private ObservableList<String> nombresProductos = obtenerNombresProductosFinales().sorted();
    private ObservableList<String> nombresRecetas = obtenerNombresRecetas().sorted();
    private ObservableList<String> nombresFilms = obtenerNombresFilms().sorted();
    private ObservableList<String> nombresCajas = obtenerNombresCajas().sorted();

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
            listaProductos.setPlaceholder(new Label(""));
            this.colLoteProducto.setCellValueFactory(new PropertyValueFactory("lote_producto"));
            this.colNombreProducto.setCellValueFactory(new PropertyValueFactory("nombre_producto"));
            this.colCantidadProducto.setCellValueFactory(new PropertyValueFactory("cantidad_producto"));
            this.cbProducto.setItems(nombresProductos);
            this.cbReceta.setItems(nombresRecetas);
            this.cbCaja.setItems(nombresCajas);
            this.cbFilm.setItems(nombresFilms);
            labelFechaProd.setText(LocalDate.now().toString());
        }
    }

    public void seleccionar(MouseEvent mouseEvent) {

    }

    public void alta(ActionEvent actionEvent) {
        if(!camposVacios()){

        }else{
            mostrarAlertError(new ActionEvent(), "Debe rellenar todos los campos");
        }
    }
    //Todos los cálculos se hacen en base a que las recetas son para masas de 1000kg
    public void enterUnidades(ActionEvent actionEvent) {
        ProductoFinal pf = getProductoFinal(new ProductoFinalDAO().getProductoFinalPorNombre(cbProducto.getValue().replaceAll(" ", "%20")).getJSONObject(0));
        Receta r = getReceta(new RecetaDAO().getRecetaPorNombre(cbReceta.getValue().replaceAll(" ", "%20")).getJSONObject(0));
        Producto caja = getProducto(new ProductoDAO().getProductoPorNombre(cbCaja.getValue().replaceAll(" ", "%20")).getJSONObject(0));
        Producto film = getProducto(new ProductoDAO().getProductoPorNombre(cbCaja.getValue().replaceAll(" ", "%20")).getJSONObject(0));
        Float pesoMasa = Float.parseFloat(tfUnidades.getText())*pf.getPaquetes_por_caja()*pf.getUnidades_por_paquete()*pf.getUnidades_por_paquete();
        //Obtener materias primas que se consumen
        ArrayList<Utiliza> consumos = obtenerConsumosReceta(new RecetaDAO().getUsos(r.getId_receta()));
        //Filtrar las ultimas MP de cada tipo recibidas (FIFO) y calcular si se pueden hacer los consumos
        //Crear objetos y .add a la lista para rellenar la lista de consumos
    }
    public void enterDias(ActionEvent actionEvent) {
        try{
            labelFechaCad.setText(LocalDate.now().plusDays(Integer.parseInt(tfDias.getText())).toString());
        }catch(NumberFormatException e){
            System.out.println("El número introducido no es un entero");
            labelFechaCad.setText("");
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
     * Castea un JSON a un objeto de tipo Receta
     * @param jsonproductos
     * @return Receta
     */
    public Receta getReceta(JSONObject jsonproductos){
        Long id = Long.parseLong(jsonproductos.get("id_receta").toString());
        String nombre = jsonproductos.get("nombre").toString();

        return new Receta(id, nombre);
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
     * Hace una llamada a la API para obtener todas las recetas e inserta sus nombres en una ObservableList<String>
     * @return ObservableList<String> con los nombres de las recetas de la base de datos
     */
    public ObservableList<String> obtenerNombresRecetas(){
        ObservableList<String> recetas = FXCollections.observableArrayList();
        JSONArray arrayRecetas = new RecetaDAO().getAllRecetas();
        for(int i = 0 ; i < arrayRecetas.length(); i++){
            recetas.add(getReceta(arrayRecetas.getJSONObject(i)).getNombre());
        }
        return recetas;
    }

    /**
     * Hace una llamada a la API para obtener todos los tipos de caja e inserta sus nombres en una ObservableList<String>
     * @return ObservableList<String> con los nombres de las cajas finales de la base de datos
     */
    public ObservableList<String> obtenerNombresCajas(){
        ObservableList<String> cajas = FXCollections.observableArrayList();
        JSONArray arrayCajas = new ProductoDAO().getProductosPorTipo("CAJA");
        for(int i = 0 ; i < arrayCajas.length(); i++){
            cajas.add(getProducto(arrayCajas.getJSONObject(i)).getNombre());
        }
        return cajas;
    }

    /**
     * Hace una llamada a la API para obtener los films e inserta sus nombres en una ObservableList<String>
     * @return ObservableList<String> con los nombres de los films de la base de datos
     */
    public ObservableList<String> obtenerNombresFilms(){
        ObservableList<String> films = FXCollections.observableArrayList();
        JSONArray arrayFilms = new ProductoDAO().getProductosPorTipo("ENV");
        for(int i = 0 ; i < arrayFilms.length(); i++){
            films.add(getProducto(arrayFilms.getJSONObject(i)).getNombre());
        }
        return films;
    }

    /**
     * @param jsonconsumos
     * @return Un ArrayList de UtilizaDTO
     */
    public ArrayList<Utiliza> obtenerConsumosReceta(JSONArray jsonconsumos){
        ArrayList<Utiliza> consumos = new ArrayList<>();
        for(int i = 0; i<jsonconsumos.length(); i++){
            Long id = Long.parseLong(jsonconsumos.getJSONObject(i).get("id_uso").toString());
            JSONObject prod = (JSONObject) jsonconsumos.getJSONObject(i).get("producto");
            Producto producto = new Producto(Long.parseLong(prod.get("id_producto").toString()), prod.get("nombre").toString(), prod.get("tipo").toString());
            Float cantidad = Float.parseFloat(jsonconsumos.getJSONObject(i).get("cantidad_mp").toString());
            consumos.add(new Utiliza(id, null, producto, cantidad));
        }
        return consumos;
    }

    public boolean camposVacios(){
        boolean vacios = false;
        if(labelFechaCad.getText().equals("") || listaProductos.getItems().equals(null)){
            vacios = true;
        }
        return vacios;
    }

    private void mostrarAlertError(ActionEvent event, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
