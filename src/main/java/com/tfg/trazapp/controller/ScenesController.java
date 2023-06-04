package com.tfg.trazapp.controller;

import com.tfg.trazapp.model.vo.Usuario;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ScenesController {
    private Stage principal;
    public Usuario user;
    public ScenesController(Stage stage) throws IOException {
        this.principal = stage;
        cargarLogin();
    }
    public void cargarLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/tfg/trazapp/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        principal.setTitle("trazApp");
        principal.getIcons().add(new Image(getClass().getResourceAsStream("/com/tfg/trazapp/images/LOGO3.png")));
        principal.setScene(scene);
        principal.show();
        //seteamos el controller en el login para poder cambiar a la siguiente scene
        LoginController lc = fxmlLoader.getController();
        lc.setSc(this);
    }
    public void cargarPrincipal() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/tfg/trazapp/principal-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        principal.setTitle("trazApp");
        principal.getIcons().add(new Image(getClass().getResourceAsStream("/com/tfg/trazapp/images/LOGO3.png")));
        principal.setScene(scene);
        PrincipalController pc = fxmlLoader.getController();
        pc.setSc(this);
        pc.user=user;
        //Ocultamos las pestañas de los desplegables basándonos en el rol del usuario
        if(!user.getRol().equals("ADMIN")) { //No es admin
            pc.usuarios.setVisible(false);
            if (!user.getRol().equals("IDT")) { //No es tecnico I+D
                pc.gestionProductos.setVisible(false);
                if (!user.getRol().equals("OFICINAS")) { //No es personal de administración/oficinas
                    pc.gestionProveedores.setVisible(false);
                    pc.gestionClientes.setVisible(false);
                }
            }
        }
        principal.show();
    }
}
