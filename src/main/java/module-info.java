module com.tfg.trazapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires commons.dbcp2;
    requires org.json;
    requires org.apache.commons.lang3;

    exports com.tfg.trazapp;
    exports com.tfg.trazapp.controller;
    exports com.tfg.trazapp.model.vo;
    exports com.tfg.trazapp.model.dto;
    opens com.tfg.trazapp to javafx.fxml;
    opens com.tfg.trazapp.model.vo to javafx.fxml;
    opens com.tfg.trazapp.model.dto to javafx.fxml;
    opens com.tfg.trazapp.controller to javafx.fxml;
}