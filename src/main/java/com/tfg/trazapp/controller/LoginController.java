package com.tfg.trazapp.controller;

import com.tfg.trazapp.model.vo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    ScenesController sc;
    private Usuario user = new Usuario(0l, "", "");
    @FXML
    private TextField tfUser;
    @FXML
    private PasswordField tfPass;
    @FXML
    private Button btnLogin;

    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        Object evt = event.getSource();
        if(evt.equals(btnLogin)){
            if(tfUser.getText().equals("") || tfPass.getText().equals("")){
                mostrarAlertError(new ActionEvent(), "Debe rellenar todos los campos");
            }else{
                user = user.comprobarUsuario(tfUser.getText(), tfPass.getText());
                if(user != null){
                    sc.user = user;
                    sc.cargarPrincipal();
                }else{
                    mostrarAlertError(new ActionEvent(), "El usuario o la contraseña son incorrectos");
                    user = new Usuario(0l, "", "");
                }
            }
        }
    }

    @FXML
    private void mostrarAlertError(ActionEvent event, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public ScenesController getSc() {
        return sc;
    }
    public void setSc(ScenesController sc) {
        this.sc = sc;
    }
}