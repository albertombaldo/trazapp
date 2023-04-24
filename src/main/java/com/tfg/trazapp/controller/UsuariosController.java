package com.tfg.trazapp.controller;

import com.tfg.trazapp.model.vo.Producto;
import com.tfg.trazapp.model.vo.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UsuariosController implements Initializable {
    @FXML
    private Button btnAlta;
    @FXML
    private Button btnDel;
    @FXML
    private Button btnFiltrar;
    @FXML
    private Button btnMod;
    @FXML
    private ComboBox cbRol;
    @FXML
    private ComboBox cbRolFiltro;
    @FXML
    private TableColumn colIdUsuario;
    @FXML
    private TableColumn colRolUsuario;
    @FXML
    private TableColumn colNombreUsuario;
    @FXML
    private GridPane gpCampos;
    @FXML
    private GridPane gpLista;
    @FXML
    private HBox hbBotones;
    @FXML
    private HBox hboxFiltro;
    @FXML
    private TableView<Usuario> listaUsuarios;
    @FXML
    private TextField pfPass;
    @FXML
    private TextField pfPassRep;
    @FXML
    private TextField tfNombreUsuarioFiltro;
    @FXML
    private TextField tfNombreUsuario;

    private ObservableList<String> rolesUsuario = FXCollections.observableArrayList("ADMIN", "IDT", "QAT", "OPERARIO", "OFICINAS");
    private ObservableList<String> rolesUsuarioFiltrar = FXCollections.observableArrayList("ADMIN", "IDT", "QAT", "OPERARIO", "OFICINAS", "TODOS");

    private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(listaUsuarios != null){
            this.colIdUsuario.setCellValueFactory(new PropertyValueFactory("id_usuario"));
            this.colNombreUsuario.setCellValueFactory(new PropertyValueFactory("nombre"));
            this.colRolUsuario.setCellValueFactory(new PropertyValueFactory("rol"));
            this.cbRol.setItems(rolesUsuario);
            this.cbRolFiltro.setItems(rolesUsuarioFiltrar);
            mostrarUsuarios(obtenerUsuarios());
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<Usuario> obtenerUsuarios(){
        ArrayList<Usuario> users = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/trazapp", "root", "");
            System.out.println("Se ha conectado correctamente a la base de datos");
            if(conn == null){
                System.out.println("No se ha podido conectar a la base de datos");
            }else {
                ResultSet rs = conn.prepareStatement("select * from usuarios;").executeQuery();
                while (rs.next()) {
                    users.add(new Usuario(rs.getLong(1), rs.getString(2), rs.getString(4)));
                }
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    public void mostrarUsuarios(ArrayList<Usuario> users){
        usuarios.clear();
        listaUsuarios.setPlaceholder(new Label("No se han encontrado resultados para su búsqueda"));
        for(Usuario u : users){
            usuarios.add(u);
        }
        this.listaUsuarios.setItems(usuarios);
    }
    ///////////////////////////////////////////////////////////////////////////////////
    public void clickAction(ActionEvent actionEvent) {
        Object evt = actionEvent.getSource();
        if(evt.equals(btnFiltrar)){
            mostrarUsuarios(filtrarUsuarios());
        }else if(evt.equals(btnAlta)){
            if(!this.tfNombreUsuario.getText().equals("") && this.cbRol.getValue()!=null && !this.pfPass.getText().equals("") && !this.pfPassRep.getText().equals("")){
                if(this.pfPass.getText().equals(this.pfPassRep.getText())){  //Comprobamos que las contraseñas coinciden
                    alta();
                }else{
                    mostrarAlertError(new ActionEvent(), "Las contraseñas deben ser iguales");
                }
            }else{
                mostrarAlertError(new ActionEvent(), "Debe rellenar todos los campos");
            }
        }else if(evt.equals(btnDel)){
            if(this.tfNombreUsuario.getText().equals("") && this.cbRol.getValue()!=null){
                eliminar();
            }else{
                mostrarAlertError(new ActionEvent(), "Debe seleccionar un usuario de la lista");
            }
        }else if(evt.equals(btnMod)){
            if(this.tfNombreUsuario.getText().equals("") && this.cbRol.getValue()!=null && !this.pfPass.getText().equals("") && !this.pfPassRep.getText().equals("")){
                if(this.pfPass.getText().equals(this.pfPassRep.getText())){  //Comprobamos que las contraseñas coinciden
                    modificar();
                }else{
                    mostrarAlertError(new ActionEvent(), "Las contraseñas deben ser iguales");
                }
            }else{
                mostrarAlertError(new ActionEvent(), "Debe rellenar todos los campos");
            }
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////
    public void seleccionar(MouseEvent mouseEvent) {
        Usuario u = listaUsuarios.getSelectionModel().getSelectedItem();
        if(u != null){
            this.tfNombreUsuario.setText(u.getNombre());
            this.cbRol.setValue(u.getRol());
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////
    public ArrayList<Usuario> filtrarUsuarios(){
        ArrayList<Usuario> users = new ArrayList<>();
        Connection conn;
        String nombre = this.tfNombreUsuarioFiltro.getText();
        String rol = null;
        if(this.cbRolFiltro.getValue() != null){
            rol = this.cbRolFiltro.getValue().toString();
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/trazapp", "root", "");
            System.out.println("Se ha conectado correctamente a la base de datos");
            if(conn == null){
                System.out.println("No se ha podido conectar a la base de datos");
            }else {
                ResultSet rs;
                if(nombre != null && rol != null && !rol.equals("TODOS")){
                    rs = conn.prepareStatement("select * from usuarios where nombre like '%" +nombre+ "%' and rol = '"+rol+"';").executeQuery();
                }else if (nombre == null && rol == null){
                    rs = conn.prepareStatement("select * from usuarios;").executeQuery();
                }else if(nombre != null || rol.equals("TODOS")){
                    rs = conn.prepareStatement("select * from usuarios where nombre like '%" +nombre+ "%';").executeQuery();
                }else{
                    rs = conn.prepareStatement("select * from usuarios where rol = '"+rol+"';").executeQuery();
                }
                while (rs.next()) {
                    users.add(new Usuario(rs.getLong(1), rs.getString(2), rs.getString(4)));
                }
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    ///////////////////////////////////////////////////////////////////////////////////
    public void eliminar() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/trazapp", "root", "");
            System.out.println("Se ha conectado correctamente a la base de datos");
            if(conn == null){
                System.out.println("No se ha podido conectar a la base de datos");
            }else {
                conn.prepareStatement("delete from usuarios where nombre = '"+this.tfNombreUsuario.getText()+"';").executeUpdate();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////
    public void modificar() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/trazapp", "root", "");
            System.out.println("Se ha conectado correctamente a la base de datos");
            if(conn == null){
                System.out.println("No se ha podido conectar a la base de datos");
            }else {
                conn.prepareStatement("update usuarios set nombre='"+this.tfNombreUsuario.getText()+"', pass='"+this.pfPass.getText()+"', rol='"+this.cbRol.getValue().toString()+"' where nombre = '"+this.tfNombreUsuario.getText()+"';").executeUpdate();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////
    public void alta() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/trazapp", "root", "");
            System.out.println("Se ha conectado correctamente a la base de datos");
            if(conn == null){
                System.out.println("No se ha podido conectar a la base de datos");
            }else {
                conn.prepareStatement("insert into usuarios (id_usuario, nombre, pass, rol) values (0, '"+this.tfNombreUsuario.getText()+"',MD5('"+this.pfPass.getText()+"'), '"+this.cbRol.getValue().toString()+"' )").executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //////////////////////////////////////////////////////////////////////////////////
    private void mostrarAlertError(ActionEvent event, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
