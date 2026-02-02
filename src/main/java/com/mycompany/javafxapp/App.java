package com.mycompany.javafxapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Label hello = new Label("Hello, JavaFX!");
        StackPane root = new StackPane(hello);
        Scene scene = new Scene(root, 400, 300);

        stage.setTitle("JavaFX Maven App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}