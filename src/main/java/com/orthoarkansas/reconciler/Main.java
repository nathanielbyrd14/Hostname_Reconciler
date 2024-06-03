package com.orthoarkansas.reconciler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    /**
     * Main entry point for JavaFX App
     * @param stage Initial stage passed in by JavaFX
     * @throws IOException When FXMLLoader fails to load the fxml file for the home scene
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home-view.fxml"));

        Scene scene = new Scene(root, 325, 100);

        stage.setTitle("Reconciler");
        stage.setMinHeight(scene.getHeight() + 25);
        stage.setMinWidth(scene.getWidth());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}