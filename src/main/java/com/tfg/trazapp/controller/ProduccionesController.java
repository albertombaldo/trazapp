package com.tfg.trazapp.controller;

import com.tfg.trazapp.model.dao.*;
import com.tfg.trazapp.model.dto.ConsumeDTO;
import com.tfg.trazapp.model.vo.*;
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
import java.sql.Date;
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
    private ComboBox<String> cbProducto;
    @FXML
    private ComboBox<String> cbReceta;
    @FXML
    private ComboBox<String> cbCaja;
    @FXML
    private ComboBox<String> cbFilm;
    @FXML
    private TableView<ConsumeDTO> listaProductos;
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
    private TextField tfUnidades;
    @FXML
    private TextField tfDias;
    private ObservableList<String> nombresProductos = obtenerNombresProductosFinales().sorted();
    private ObservableList<String> nombresRecetas = obtenerNombresRecetas().sorted();
    private ObservableList<String> nombresFilms = obtenerNombresFilms().sorted();
    private ObservableList<String> nombresCajas = obtenerNombresCajas().sorted();
    private ObservableList<ConsumeDTO> consumosProduccion = FXCollections.observableArrayList();
    private ObservableList<Suministro> suministrosTrasProduccion = FXCollections.observableArrayList();

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
            //Actualizamos el stock de los suministros
            for(Suministro s : suministrosTrasProduccion){
                new SuministroDAO().anadirSumistro(s);
            }
        }else{
            mostrarAlertError(new ActionEvent(), "Debe rellenar todos los campos");
        }
    }
    //Todos los cálculos se hacen en base a que las recetas son para masas de 1000kg
    public void enterUnidades(ActionEvent actionEvent) {
        suministrosTrasProduccion.clear();
        consumosProduccion.clear();
        boolean hayMateriasSufiientes = true;
        ProductoFinal pf = getProductoFinal(new ProductoFinalDAO().getProductoFinalPorNombre(cbProducto.getValue().replaceAll(" ", "%20")).getJSONObject(0));
        Receta r = getReceta(new RecetaDAO().getRecetaPorNombre(cbReceta.getValue().replaceAll(" ", "%20")).getJSONObject(0));
        Producto caja = getProducto(new ProductoDAO().getProductoPorNombre(cbCaja.getValue().replaceAll(" ", "%20")).getJSONObject(0));
        Producto film = getProducto(new ProductoDAO().getProductoPorNombre(cbFilm.getValue().replaceAll(" ", "%20")).getJSONObject(0));
        Float pesoMasa = Float.parseFloat(tfUnidades.getText())*pf.getPaquetes_por_caja()*pf.getUnidades_por_paquete()*pf.getPeso_por_unidad();
        System.out.println(pesoMasa);
        //Obtener materias primas que se consumen
        ArrayList<Utiliza> consumos = obtenerConsumosReceta(new RecetaDAO().getUsos(r.getId_receta()));
        consumos.add(new Utiliza(null, null, caja, Float.parseFloat(tfUnidades.getText())));
        consumos.add(new Utiliza(null, null, film, Float.parseFloat(tfUnidades.getText())));
        //Filtrar las últimas MP de cada tipo recibidas (FIFO) y calcular si se pueden hacer los consumos
        for(Utiliza uso : consumos){
            Producto p = uso.getProducto();
            if(!p.getNombre().equals("Agua") && p.getTipo().equals("MP")){ //Al ser agua corriente en la BD la cantidad es 0, asi que se asume que siempre hay stock
                JSONArray suministros = new SuministroDAO().getUltinmosSuministrosPorFechaAsc(p.getId_producto());
                Float cantidadNecesaria = (pesoMasa/1000)*uso.getCantidad_mp();
                ArrayList<Suministro> sums = new ArrayList<>();
                boolean cantidadSuficiente = false;
                int cont = 0;
                //Rellenamos el array con los suministros disponibles de la materia prima a consumir
                for(int i = 0; i<suministros.length(); i++){
                    Suministro s = getSuministro(suministros.getJSONObject(i));
                    sums.add(s);
                }
                //Comprobamos las cantidades si son suficientes
                while(!cantidadSuficiente && cont<sums.size()){
                    if((sums.get(cont).getCantidad_stock() - cantidadNecesaria)>0){
                        sums.get(cont).setCantidad_stock(sums.get(cont).getCantidad_stock() - cantidadNecesaria);
                        suministrosTrasProduccion.add(sums.get(cont));
                        consumosProduccion.add(new ConsumeDTO(sums.get(cont).getLote_producto(), p.getNombre(), cantidadNecesaria));
                        cantidadSuficiente = true;
                    }else{ //Si no hay suficiente MP, se toma parte del lote siguiente para completar la produccion, por lo que se actualiza cantidadNecesaria para comprobar si el siguiente lote tiene stock
                        consumosProduccion.add(new ConsumeDTO(sums.get(cont).getLote_producto(), p.getNombre(), sums.get(cont).getCantidad_stock()));
                        cantidadNecesaria = cantidadNecesaria - sums.get(cont).getCantidad_stock();
                        cont++;
                    }
                }
                if(!cantidadSuficiente){
                    mostrarAlertError(new ActionEvent(), "No hay suficiente " + p.getNombre() + " para completar la producción");
                    hayMateriasSufiientes = false;
                }
            }else if(p.getNombre().equals("Agua")){
                consumosProduccion.add(new ConsumeDTO(null, p.getNombre(), (pesoMasa/1000)*uso.getCantidad_mp()));
            }else{ //Sacar lote de los envases
                consumosProduccion.add(new ConsumeDTO(null, p.getNombre(), uso.getCantidad_mp()));

            }
        }
        if(hayMateriasSufiientes)
            this.listaProductos.setItems(consumosProduccion);
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
     * Castea un JSON a un objeto de tipo Producto
     * @param jsonsuministros
     * @return Producto
     */
    public Suministro getSuministro(JSONObject jsonsuministros){
        Long id = Long.parseLong(jsonsuministros.get("id_suministro").toString());
        Date fecha_recepcion = Date.valueOf(jsonsuministros.get("fecha_recepcion").toString());
        Date fecha_caducidad = null;
        if(!jsonsuministros.get("fecha_caducidad").toString().equals("null")){
            fecha_caducidad = Date.valueOf(jsonsuministros.get("fecha_caducidad").toString());
        }
        JSONObject prod = (JSONObject) jsonsuministros.get("producto");
        Producto producto = new Producto(Long.parseLong(prod.get("id_producto").toString()), prod.get("nombre").toString(), prod.get("tipo").toString());
        JSONObject prov = (JSONObject) jsonsuministros.get("proveedor");
        Proveedor proveedor = new Proveedor(Long.parseLong(prov.get("id_proveedor").toString()), prov.get("nombre").toString(), prov.get("nif").toString(), prov.get("telefono").toString(), prov.get("direccion").toString());
        String albaran = jsonsuministros.get("albaran").toString();
        float cantidad_recepcionada = Float.parseFloat(jsonsuministros.get("cantidad_recepcionada").toString());
        float cantidad_stock = Float.parseFloat(jsonsuministros.get("cantidad_stock").toString());
        String lote_producto = jsonsuministros.get("lote_producto").toString();

        return new Suministro(id, fecha_recepcion, fecha_caducidad, proveedor, producto, albaran, cantidad_recepcionada, cantidad_stock, lote_producto);
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
