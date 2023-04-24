package com.tfg.trazapp;

import com.tfg.trazapp.controller.ScenesController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class MainTrazapp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        new ScenesController(stage);
    }
    public static void main(String[] args) {
        launch(args);
    }
}